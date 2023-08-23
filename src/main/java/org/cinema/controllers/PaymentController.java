package org.cinema.controllers;

import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import org.cinema.services.data.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
  @Autowired private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping("/moviePublic")
  public Response getListMoviePublicForPayment(
      @QueryParam("movieUuid") UUID movieUuid,
      @QueryParam("startDate") String startDate,
      @QueryParam("branchUuid") UUID branchUuid,
      @QueryParam("moviePublicUuid") UUID moviePublicUuid) {
    return paymentService.getListMoviePublicForPayment(
        movieUuid, startDate, branchUuid, moviePublicUuid);
  }
}
