package org.cinema.controllers;

import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import org.cinema.models.dto.MovieDto;
import org.cinema.models.dto.MoviePublicDto;
import org.cinema.services.data.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
  @Autowired private final MovieService movieService;

  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping()
  public Response getListMovies(
      @RequestParam(defaultValue = "1") int active, @RequestParam(required = false) String name) {
    return movieService.getListMovies(active, name);
  }

  @GetMapping("/{movieUuid}")
  public Response getMovie(@PathVariable("movieUuid") UUID movieUuid) {
    return movieService.getMovie(movieUuid);
  }

  @PostMapping()
  public Response create(@RequestBody MovieDto newMovie) {
    return movieService.create(newMovie);
  }

  @PatchMapping("/{movieUuid}")
  public Response updateMovie(
      @PathVariable("movieUuid") UUID moveUuid, @RequestBody MovieDto movieUpdate) {
    return movieService.updateMovie(moveUuid, movieUpdate);
  }

  @PostMapping("/active")
  public Response changeMovieActive(
      @RequestBody List<UUID> movieUuids,
      @RequestParam(name = "active", required = true) int active) {
    return movieService.changeMovieActive(movieUuids, active);
  }

  @GetMapping("/public")
  public Response getListMoviePublic(@RequestParam(required = false) UUID branchUuid) {
    return movieService.getListMoviePublic(branchUuid);
  }

  @PostMapping("/public")
  public Response addMoviePublic(@RequestBody MoviePublicDto newMoviePublic) {
    return movieService.addMoviePublic(newMoviePublic);
  }
}
