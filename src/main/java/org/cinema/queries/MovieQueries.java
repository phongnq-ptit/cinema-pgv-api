package org.cinema.queries;

import static org.cinema.jooq.tables.MovieCategory.MOVIE_CATEGORY;
import static org.cinema.jooq.tables.MovieFile.MOVIE_FILE;
import static org.cinema.jooq.tables.Movies.MOVIES;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;
import org.cinema.models.dto.CategoryDto;
import org.cinema.models.dto.FileDto;
import org.cinema.models.dto.MovieDto;
import org.cinema.utils.CommonUtils;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationScoped
public class MovieQueries {
  @Autowired private final DSLContext dsl;

  public MovieQueries(DSLContext dsl) {
    this.dsl = dsl;
  }

  public void insert(MovieDto movie) {
    dsl.insertInto(
            MOVIES, MOVIES.UUID, MOVIES.NAME, MOVIES.DURATION, MOVIES.AUTHOR, MOVIES.RELEASE_DATE)
        .values(
            CommonUtils.uuidToBytesArray(UUID.randomUUID()),
            movie.getName(),
            movie.getDuration(),
            movie.getAuthor(),
            movie.getReleaseDate())
        .execute();

    this.insertCategoriesIntoMovie(movie, movie.getCategories());

    List<FileDto> files = movie.getImages();
    files.add(movie.getMovieFile());
    this.insertFilesIntoMovie(movie, files);
  }

  private void insertCategoriesIntoMovie(MovieDto movie, List<CategoryDto> categories) {
    for (CategoryDto category : categories) {
      dsl.insertInto(
              MOVIE_CATEGORY,
              MOVIE_CATEGORY.UUID,
              MOVIE_CATEGORY.MOVIE_UUID,
              MOVIE_CATEGORY.CATEGORY_UUID)
          .values(
              CommonUtils.uuidToBytesArray(UUID.randomUUID()),
              CommonUtils.uuidToBytesArray(movie.getUuid()),
              CommonUtils.uuidToBytesArray(category.getUuid()))
          .execute();
    }
  }

  private void insertFilesIntoMovie(MovieDto movie, List<FileDto> files) {
    for (FileDto file : files) {
      dsl.insertInto(MOVIE_FILE, MOVIE_FILE.UUID, MOVIE_FILE.MOVIE_UUID, MOVIE_FILE.FILE_UUID)
          .values(
              CommonUtils.uuidToBytesArray(UUID.randomUUID()),
              CommonUtils.uuidToBytesArray(movie.getUuid()),
              CommonUtils.uuidToBytesArray(file.getUuid()))
          .execute();
    }
  }
}
