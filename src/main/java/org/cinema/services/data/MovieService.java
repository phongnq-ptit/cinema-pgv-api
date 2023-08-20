package org.cinema.services.data;

import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import org.cinema.models.dto.FileDto;
import org.cinema.models.dto.MovieDto;
import org.cinema.models.dto.MoviePublicDto;
import org.cinema.models.response.BaseResponse;
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

  public Response getListMovies(int active) {
    return Response.ok()
        .entity(
            new BaseResponse<List<MovieDto>>(
                200, "lay ra danh sach phim thanh cong", movieQueries.findAll(active)))
        .build();
  }

  public Response addMoviePublic(MoviePublicDto moviePublicDto) {
    movieQueries.insertMoviePublic(moviePublicDto);

    return Response.noContent().build();
  }

  public Response getListMoviePublic(UUID braUuid) {
    return Response.ok()
        .entity(
            new BaseResponse<List<MoviePublicDto>>(
                200,
                "lay ra danh sach phim xuat ban thanh cong",
                movieQueries.getListMoviePublic(braUuid)))
        .build();
  }

  public Response changeMovieActive(List<UUID> movieUuids, int active) {
    movieQueries.changeMovieActive(movieUuids, active);

    return Response.noContent().build();
  }
}
