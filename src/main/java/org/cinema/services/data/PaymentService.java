package org.cinema.services.data;

import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import org.cinema.models.dto.MoviePublicDto;
import org.cinema.models.response.BaseResponse;
import org.cinema.queries.MovieQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
  @Autowired private final MovieQueries movieQueries;

  public PaymentService(MovieQueries movieQueries) {
    this.movieQueries = movieQueries;
  }

  public Response getListMoviePublicForPayment(
      UUID movieUuid, String startDate, UUID branchUuid, UUID moviePublicUuid) {
    return Response.ok()
        .entity(
            new BaseResponse<List<MoviePublicDto>>(
                200,
                "lay ra danh sach phim xuat ban thanh cong",
                movieQueries.getListMoviePublicForPayment(
                    movieUuid, startDate, branchUuid, moviePublicUuid)))
        .build();
  }
}
