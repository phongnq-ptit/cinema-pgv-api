package org.cinema.queries;

import static org.cinema.jooq.tables.Categories.CATEGORIES;
import static org.cinema.jooq.tables.Files.FILES;
import static org.cinema.jooq.tables.MovieCategory.MOVIE_CATEGORY;
import static org.cinema.jooq.tables.MovieFile.MOVIE_FILE;
import static org.cinema.jooq.tables.MoviePublic.MOVIE_PUBLIC;
import static org.cinema.jooq.tables.Movies.MOVIES;
import static org.cinema.jooq.tables.Users.USERS;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.cinema.models.dto.CategoryDto;
import org.cinema.models.dto.FileDto;
import org.cinema.models.dto.MovieDto;
import org.cinema.models.dto.MoviePublicDto;
import org.cinema.models.enums.FileType;
import org.cinema.models.records.CategoryRecord.CategoryR;
import org.cinema.models.records.FileRecord.FileR;
import org.cinema.models.records.MovieRecord.MoviePublicR;
import org.cinema.models.records.MovieRecord.MovieR;
import org.cinema.utils.CommonUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationScoped
public class MovieQueries {
  @Autowired private final DSLContext dsl;
  @Autowired private final UserQueries userQueries;

  public MovieQueries(DSLContext dsl, UserQueries userQueries) {
    this.dsl = dsl;
    this.userQueries = userQueries;
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

  public void insertMoviePublic(MoviePublicDto moviePublicDto) {
    dsl.insertInto(
            MOVIE_PUBLIC,
            MOVIE_PUBLIC.UUID,
            MOVIE_PUBLIC.MOVIE_UUID,
            MOVIE_PUBLIC.BRANCH_UUID,
            MOVIE_PUBLIC.START_DATE,
            MOVIE_PUBLIC.END_DATE,
            MOVIE_PUBLIC.PRICE,
            MOVIE_PUBLIC.TOTAL_TICKETS)
        .values(
            CommonUtils.uuidToBytesArray(UUID.randomUUID()),
            CommonUtils.uuidToBytesArray(moviePublicDto.getMovie().getUuid()),
            CommonUtils.uuidToBytesArray(moviePublicDto.getBranch().getUuid()),
            moviePublicDto.getStartDate(),
            moviePublicDto.getEndDate(),
            moviePublicDto.getPrice(),
            moviePublicDto.getTotalTickets())
        .execute();
  }

  public List<MovieDto> findAll(int active, String name) {
    var query = dsl.selectFrom(MOVIES).where(MOVIES.ACTIVE.eq(active));

    if (!name.equalsIgnoreCase("#")) {
      query = query.and(MOVIES.NAME.like("%" + name + "%"));
    }

    List<MovieR> movieRs = query.orderBy(MOVIES.ID.desc()).fetchInto(MovieR.class);

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

  public MovieDto findByUuid(UUID movieUuid) {
    MovieR movieR =
        dsl.select(MOVIES)
            .from(MOVIES)
            .where(MOVIES.UUID.eq(CommonUtils.uuidToBytesArray(movieUuid)))
            .fetchOptional()
            .map(record -> record.into(MovieR.class))
            .orElse(null);

    MovieDto movieDto = MovieDto.toDto(movieR);
    movieDto.setImages(this.getImagesOfMovie(movieDto));
    movieDto.setMovieFile(this.getVideoOfMovie(movieDto));
    movieDto.setCategories(this.getCategoriesOfMovie(movieDto));

    return movieDto;
  }

  public void update(UUID movieUuid, MovieDto movieUpdate) {
    MovieDto movie = this.findByUuid(movieUuid);

    if (Objects.isNull(movie)) return;

    dsl.update(MOVIES)
        .set(MOVIES.NAME, movieUpdate.getName())
        .set(MOVIES.AUTHOR, movieUpdate.getAuthor())
        .set(MOVIES.DURATION, movieUpdate.getDuration())
        .set(MOVIES.RELEASE_DATE, movieUpdate.getReleaseDate())
        .set(MOVIES.ACTIVE, movieUpdate.getActive())
        .where(MOVIES.UUID.eq(CommonUtils.uuidToBytesArray(movie.getUuid())))
        .execute();

    // update category
    this.removeCategoriesOfMovie(
        movie.getCategories().stream().map(item -> item.getUuid()).toList(),
        movie.getUuid()); // remove old
    this.insertCategoriesIntoMovie(movie, movieUpdate.getCategories()); // insert new

    // update images
    if (Objects.nonNull(movieUpdate.getImages())) {
      this.removeFilesOfMovie(
          movie.getImages().stream().map(item -> item.getUuid()).toList(),
          movie.getUuid()); // remove old
      this.insertFilesIntoMovie(movie, movieUpdate.getImages()); // insert new
    }

    // update video
    if (Objects.nonNull(movieUpdate.getMovieFile())) {
      this.removeFilesOfMovie(
          Arrays.asList(movie.getMovieFile().getUuid()), movie.getUuid()); // remove old
      this.insertFilesIntoMovie(movie, Arrays.asList(movieUpdate.getMovieFile())); // insert new
    }
  }

  public void updateMoviePublic(UUID moviePublicUuid, MoviePublicDto moviePublicUpdate) {
    MoviePublicDto moviePublic = this.findMoviePublicByUuid(moviePublicUuid);

    if (Objects.isNull(moviePublic)) return;

    dsl.update(MOVIE_PUBLIC)
        .set(MOVIE_PUBLIC.START_DATE, moviePublicUpdate.getStartDate())
        .set(MOVIE_PUBLIC.END_DATE, moviePublicUpdate.getEndDate())
        .set(MOVIE_PUBLIC.PRICE, moviePublicUpdate.getPrice())
        .set(MOVIE_PUBLIC.TOTAL_TICKETS, moviePublicUpdate.getTotalTickets())
        .where(MOVIE_PUBLIC.UUID.eq(CommonUtils.uuidToBytesArray(moviePublicUuid)))
        .execute();
  }

  public List<MoviePublicDto> getListMoviePublic(UUID branUuid, String movieName) {
    Condition dynamicMovieCondition = DSL.trueCondition();
    Condition dynamicBranchCondition = DSL.trueCondition();

    if (!movieName.equalsIgnoreCase("#")) {
      dynamicMovieCondition = dynamicMovieCondition.and(MOVIES.NAME.like("%" + movieName + "%"));
    }

    if (Objects.nonNull(branUuid)) {
      dynamicBranchCondition =
          dynamicBranchCondition.and(
              MOVIE_PUBLIC.BRANCH_UUID.eq(CommonUtils.uuidToBytesArray(branUuid)));
    }

    List<MoviePublicR> _movieRs =
        dsl.select(MOVIE_PUBLIC.fields())
            .from(MOVIE_PUBLIC)
            .join(MOVIES)
            .on(MOVIE_PUBLIC.MOVIE_UUID.eq(MOVIES.UUID))
            .and(dynamicMovieCondition)
            .join(USERS)
            .on(MOVIE_PUBLIC.BRANCH_UUID.eq(USERS.UUID))
            .and(dynamicBranchCondition)
            .fetch()
            .into(MoviePublicR.class);

    List<MoviePublicDto> result = new ArrayList<>();
    for (MoviePublicR _movieR : _movieRs) {
      MoviePublicDto dto = MoviePublicDto.toDto(_movieR);
      dto.setBranch(userQueries.findByUuid(_movieR.branchUuid()));
      dto.setMovie(this.findByUuid(_movieR.movieUuid()));
      result.add(dto);
    }

    return result;
  }

  public List<MoviePublicDto> getListMoviePublicForPayment(
      UUID movieUuid, String startDate, UUID branchUuid, UUID moviePublicUuid) {
    Condition dynamicCondition =
        DSL.trueCondition().and(MOVIE_PUBLIC.START_DATE.ge(LocalDateTime.now()));

    if (Objects.nonNull(movieUuid)) {
      dynamicCondition =
          dynamicCondition.and(MOVIE_PUBLIC.MOVIE_UUID.eq(CommonUtils.uuidToBytesArray(movieUuid)));
    }

    if (Objects.nonNull(startDate)) {
      dynamicCondition =
          dynamicCondition.and(
              MOVIE_PUBLIC.START_DATE.eq(LocalDateTime.parse(startDate.replace("Z", ""))));
    }

    if (Objects.nonNull(branchUuid)) {
      dynamicCondition =
          dynamicCondition.and(
              MOVIE_PUBLIC.BRANCH_UUID.eq(CommonUtils.uuidToBytesArray(branchUuid)));
    }

    if (Objects.nonNull(moviePublicUuid)) {
      System.out.println(121212);
      dynamicCondition =
          dynamicCondition.and(MOVIE_PUBLIC.UUID.eq(CommonUtils.uuidToBytesArray(moviePublicUuid)));
    }

    List<MoviePublicR> moviePublicRs = new ArrayList<>();
    try {
      moviePublicRs =
          dsl.selectFrom(MOVIE_PUBLIC)
              .where(dynamicCondition)
              .orderBy(MOVIE_PUBLIC.START_DATE)
              .fetch()
              .into(MoviePublicR.class);
    } catch (Exception e) {
      System.out.println(e);
    }

    List<MoviePublicDto> result = new ArrayList<>();
    for (MoviePublicR _movieR : moviePublicRs) {
      MoviePublicDto dto = MoviePublicDto.toDto(_movieR);
      dto.setBranch(userQueries.findByUuid(_movieR.branchUuid()));
      dto.setMovie(this.findByUuid(_movieR.movieUuid()));
      result.add(dto);
    }

    return result;
  }

  public List<MovieDto> getListMoviePublicForClient(
      String movieName, String branches, String categories) {
    Condition dynamicMovieCondition = DSL.trueCondition();
    Condition dynamicBranchCondition = DSL.trueCondition();
    Condition dynamicCategoryCondition = DSL.trueCondition();

    if (!movieName.equalsIgnoreCase("#")) {
      dynamicMovieCondition = dynamicMovieCondition.and(MOVIES.NAME.like("%" + movieName + "%"));
    }

    if (!branches.equalsIgnoreCase("#")) {
      List<UUID> branchUuids = CommonUtils.convertStringToListUUID(branches);
      dynamicBranchCondition =
          dynamicBranchCondition.and(
              MOVIE_PUBLIC.BRANCH_UUID.in(CommonUtils.listUuidToListBytesArray(branchUuids)));
    }

    if (!categories.equalsIgnoreCase("#")) {
      List<UUID> categoryUuids = CommonUtils.convertStringToListUUID(categories);
      dynamicCategoryCondition =
          dynamicCategoryCondition.and(
              MOVIE_CATEGORY.CATEGORY_UUID.in(CommonUtils.listUuidToListBytesArray(categoryUuids)));
    }

    /* cai cau nay dang chuan */
    // List<MovieR> _movieRs =
    //     dsl.selectDistinct(MOVIES.fields())
    //         .from(MOVIE_PUBLIC)
    //         .join(MOVIES)
    //         .on(MOVIE_PUBLIC.MOVIE_UUID.eq(MOVIES.UUID))
    //         .and(dynamicMovieCondition)
    //         .fetch()
    //         .into(MovieR.class);

    List<MovieR> _movieRs =
        dsl.selectDistinct(MOVIES.fields())
            .from(MOVIE_PUBLIC)
            .join(MOVIES)
            .on(MOVIE_PUBLIC.MOVIE_UUID.eq(MOVIES.UUID))
            .and(dynamicMovieCondition)
            .join(MOVIE_CATEGORY)
            .on(MOVIE_CATEGORY.MOVIE_UUID.eq(MOVIE_PUBLIC.MOVIE_UUID))
            .and(dynamicCategoryCondition)
            .where(dynamicBranchCondition)
            .fetch()
            .into(MovieR.class);

    List<MovieDto> result = new ArrayList<>();
    for (MovieR _movieR : _movieRs) {
      MovieDto dto = MovieDto.toDto(_movieR);
      dto.setImages(this.getImagesOfMovie(dto));
      dto.setMovieFile(this.getVideoOfMovie(dto));
      dto.setCategories(this.getCategoriesOfMovie(dto));
      result.add(dto);
    }

    return result;
  }

  public MoviePublicDto findMoviePublicByUuid(UUID moviePublicUuid) {
    MoviePublicR row =
        dsl.selectFrom(MOVIE_PUBLIC)
            .where(MOVIE_PUBLIC.UUID.eq(CommonUtils.uuidToBytesArray(moviePublicUuid)))
            .fetchOptional()
            .map(record -> record.into(MoviePublicR.class))
            .orElse(null);

    MoviePublicDto dto = MoviePublicDto.toDto(row);
    dto.setBranch(userQueries.findByUuid(row.branchUuid()));
    dto.setMovie(this.findByUuid(row.movieUuid()));
    return dto;
  }

  public void changeMovieActive(List<UUID> movieUuids, int active) {
    for (UUID movieUuid : movieUuids) {
      dsl.update(MOVIES)
          .set(MOVIES.ACTIVE, active)
          .where(MOVIES.UUID.eq(CommonUtils.uuidToBytesArray(movieUuid)))
          .execute();
    }
  }

  public void removeMoviePublic(List<UUID> moviePublicUuids) {
    try {
      dsl.deleteFrom(MOVIE_PUBLIC)
          .where(MOVIE_PUBLIC.UUID.in(CommonUtils.listUuidToListBytesArray(moviePublicUuids)))
          .execute();
    } catch (Exception e) {
      System.out.println(e);
    }
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

  private void removeFilesOfMovie(List<UUID> fileUuids, UUID movieUuid) {
    try {
      dsl.deleteFrom(MOVIE_FILE)
          .where(MOVIE_FILE.FILE_UUID.in(CommonUtils.listUuidToListBytesArray(fileUuids)))
          .and(MOVIE_FILE.MOVIE_UUID.eq(CommonUtils.uuidToBytesArray(movieUuid)))
          .execute();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private void removeCategoriesOfMovie(List<UUID> categoryUuids, UUID movieUuid) {
    try {
      dsl.deleteFrom(MOVIE_CATEGORY)
          .where(
              MOVIE_CATEGORY.CATEGORY_UUID.in(CommonUtils.listUuidToListBytesArray(categoryUuids)))
          .and(MOVIE_CATEGORY.MOVIE_UUID.eq(CommonUtils.uuidToBytesArray(movieUuid)))
          .execute();
    } catch (Exception e) {
      System.out.println(e);
    }
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
