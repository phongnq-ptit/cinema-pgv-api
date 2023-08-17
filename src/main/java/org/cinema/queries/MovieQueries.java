package org.cinema.queries;

import static org.cinema.jooq.tables.Categories.CATEGORIES;
import static org.cinema.jooq.tables.Files.FILES;
import static org.cinema.jooq.tables.MovieCategory.MOVIE_CATEGORY;
import static org.cinema.jooq.tables.MovieFile.MOVIE_FILE;
import static org.cinema.jooq.tables.Movies.MOVIES;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.cinema.models.dto.CategoryDto;
import org.cinema.models.dto.FileDto;
import org.cinema.models.dto.MovieDto;
import org.cinema.models.enums.FileType;
import org.cinema.models.records.CategoryRecord.CategoryR;
import org.cinema.models.records.FileRecord.FileR;
import org.cinema.models.records.MovieRecord.MovieR;
import org.cinema.utils.CommonUtils;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationScoped
public class MovieQueries {
  @Autowired private final DSLContext dsl;

  public MovieQueries(DSLContext dsl) {
    this.dsl = dsl;
  }

  public List<MovieDto> findAll() {
    List<MovieR> movieRs = dsl.selectFrom(MOVIES).fetchInto(MovieR.class);

    List<MovieDto> movies = new ArrayList<>();
    for (MovieR movieR : movieRs) {
      MovieDto movie = MovieDto.toDto(movieR);
      movie.setImages(this.getImagesOfMovie(movie));
      movie.setMovieFile(this.getVideoOfMovie(movie));
      movie.setCategories(this.getCategoriesOfMovie(movie));
      movies.add(movie);
    }

    return movies;
  }

  private List<FileDto> getImagesOfMovie(MovieDto movie) {
    List<FileR> _images =
        dsl.select(FILES)
            .from(FILES)
            .join(MOVIE_FILE)
            .on(FILES.UUID.eq(MOVIE_FILE.FILE_UUID))
            .where(FILES.TYPE.eq(FileType.IMAGE.getType()))
            .and(MOVIE_FILE.MOVIE_UUID.eq(CommonUtils.uuidToBytesArray(movie.getUuid())))
            .fetchInto(FileR.class);

    List<FileDto> images = new ArrayList<>();
    for (FileR image : _images) images.add(FileDto.toDto(image));
    return images;
  }

  private FileDto getVideoOfMovie(MovieDto movie) {
    FileR _video =
        dsl.select(FILES)
            .from(FILES)
            .join(MOVIE_FILE)
            .on(FILES.UUID.eq(MOVIE_FILE.FILE_UUID))
            .where(FILES.TYPE.eq(FileType.VIDEO.getType()))
            .and(MOVIE_FILE.MOVIE_UUID.eq(CommonUtils.uuidToBytesArray(movie.getUuid())))
            .fetchOptional()
            .map(record -> record.into(FileR.class))
            .orElse(null);

    return FileDto.toDto(_video);
  }

  private List<CategoryDto> getCategoriesOfMovie(MovieDto movie) {
    List<CategoryR> _categories =
        dsl.select(CATEGORIES)
            .from(CATEGORIES)
            .join(MOVIE_CATEGORY)
            .on(CATEGORIES.UUID.eq(MOVIE_CATEGORY.CATEGORY_UUID))
            .where(MOVIE_CATEGORY.MOVIE_UUID.eq(CommonUtils.uuidToBytesArray(movie.getUuid())))
            .fetchInto(CategoryR.class);

    List<CategoryDto> categories = new ArrayList<>();
    for (CategoryR category : _categories) categories.add(CategoryDto.toDto(category));
    return categories;
  }

  public void insert(MovieDto movie) {
    movie.setUuid(UUID.randomUUID());
    dsl.insertInto(
            MOVIES, MOVIES.UUID, MOVIES.NAME, MOVIES.DURATION, MOVIES.AUTHOR, MOVIES.RELEASE_DATE)
        .values(
            CommonUtils.uuidToBytesArray(movie.getUuid()),
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
