package org.cinema.services.data;

import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import org.cinema.models.dto.MoviePublicDto;
import org.cinema.models.dto.PurchaseDto;
import org.cinema.models.response.BaseResponse;
import org.cinema.queries.MovieQueries;
import org.cinema.queries.PurchaseQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
  @Autowired private final MovieQueries movieQueries;
  @Autowired private final PurchaseQueries purchaseQueries;

  public PaymentService(MovieQueries movieQueries, PurchaseQueries purchaseQueries) {
    this.movieQueries = movieQueries;
    this.purchaseQueries = purchaseQueries;
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

  public Response createPurchase(PurchaseDto dto) {
    purchaseQueries.insert(dto);
    return Response.noContent().build();
  }

  public Response getPurchaseByUuid(UUID purchaseUuid) {
    return Response.ok()
        .entity(
            new BaseResponse<PurchaseDto>(
                200, "lay ra ve cua user thanh cong", purchaseQueries.findByUuid(purchaseUuid)))
        .build();
  }

  public Response getPurchaseByUser(UUID userUuid) {
    return Response.ok()
        .entity(
            new BaseResponse<List<PurchaseDto>>(
                200,
                "lay ra danh sach ve cua user thanh cong",
                purchaseQueries.findAllByUserUuid(userUuid)))
        .build();
  }
}
