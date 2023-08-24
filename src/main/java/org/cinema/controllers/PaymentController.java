package org.cinema.controllers;

import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import org.cinema.models.dto.PurchaseDto;
import org.cinema.services.data.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
  @Autowired private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping("")
  public Response getPurchasesByUser(@QueryParam("userUuid") UUID userUuid) {
    return paymentService.getPurchaseByUser(userUuid);
  }

  @GetMapping("/{purchaseUuid}")
  public Response getPurchaseByUuid(@PathVariable("purchaseUuid") UUID purchaseUuid) {
    return paymentService.getPurchaseByUuid(purchaseUuid);
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

  @PostMapping()
  public Response createPurchase(@RequestBody PurchaseDto purchaseDto) {
    return paymentService.createPurchase(purchaseDto);
  }

  @PatchMapping("/downloads/{purchaseUuid}")
  public Response updatePurchaseDownload(@PathVariable("purchaseUuid") UUID purchaseUuid) {
    return paymentService.updateDownload(purchaseUuid);
  }
}
