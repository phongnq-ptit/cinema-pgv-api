package org.cinema.services.data;

import jakarta.ws.rs.core.Response;
import java.util.List;
import org.cinema.models.dto.FileDto;
import org.cinema.models.dto.MovieDto;
import org.cinema.queries.FileQueries;
import org.cinema.queries.MovieQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
  @Autowired private final MovieQueries movieQueries;
  @Autowired private final FileQueries fileQueries;

  public MovieService(MovieQueries movieQueries, FileQueries fileQueries) {
    this.movieQueries = movieQueries;
    this.fileQueries = fileQueries;
  }

  public Response create(MovieDto newMovie) {
    // get uuid of file
    List<FileDto> imageFiles = fileQueries.insertMultiFiles(newMovie.getImages());
    FileDto movieFile = fileQueries.insertSingleFile(newMovie.getMovieFile());
    newMovie.setImages(imageFiles);
    newMovie.setMovieFile(movieFile);

    movieQueries.insert(newMovie);

    return Response.noContent().build();
  }
}
