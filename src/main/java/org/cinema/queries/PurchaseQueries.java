package org.cinema.queries;

import static org.cinema.jooq.tables.Purchases.PURCHASES;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.cinema.models.dto.PurchaseDto;
import org.cinema.models.records.PurchaseRecord.PurchaseR;
import org.cinema.utils.CommonUtils;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationScoped
public class PurchaseQueries {
  @Autowired private final DSLContext dsl;
  @Autowired private final MovieQueries movieQueries;
  @Autowired private final UserQueries userQueries;

  public PurchaseQueries(DSLContext dsl, MovieQueries movieQueries, UserQueries userQueries) {
    this.dsl = dsl;
    this.movieQueries = movieQueries;
    this.userQueries = userQueries;
  }

  public List<PurchaseDto> findAllByUserUuid(UUID userUuid) {
    List<PurchaseR> rows =
        dsl.selectFrom(PURCHASES)
            .where(PURCHASES.USER_UUID.eq(CommonUtils.uuidToBytesArray(userUuid)))
            .orderBy(PURCHASES.ID.desc())
            .fetch()
            .into(PurchaseR.class);

    List<PurchaseDto> result = new ArrayList<>();
    for (PurchaseR row : rows) {
      PurchaseDto item = PurchaseDto.toDto(row);
      item.setMoviePublic(movieQueries.findMoviePublicByUuid(row.moviePublicUuid()));
      item.setUser(userQueries.findByUuid(row.userUuid()));
      result.add(item);
    }

    return result;
  }

  public PurchaseDto findByUuid(UUID purchaseUuid) {
    PurchaseR row =
        dsl.selectFrom(PURCHASES)
            .where(PURCHASES.UUID.eq(CommonUtils.uuidToBytesArray(purchaseUuid)))
            .fetchOptional()
            .map(record -> record.into(PurchaseR.class))
            .orElse(null);

    PurchaseDto dto = PurchaseDto.toDto(row);
    dto.setMoviePublic(movieQueries.findMoviePublicByUuid(row.moviePublicUuid()));
    dto.setUser(userQueries.findByUuid(row.userUuid()));

    return dto;
  }

  public void insert(PurchaseDto dto) {
    dto.setUuid(UUID.randomUUID());
    try {
      dsl.insertInto(
              PURCHASES,
              PURCHASES.UUID,
              PURCHASES.USER_UUID,
              PURCHASES.MOVIE_PUBLIC_UUID,
              PURCHASES.QUANTITY_OF_TICKETS,
              PURCHASES.DOWNLOADS)
          .values(
              CommonUtils.uuidToBytesArray(dto.getUuid()),
              CommonUtils.uuidToBytesArray(dto.getUser().getUuid()),
              CommonUtils.uuidToBytesArray(dto.getMoviePublic().getUuid()),
              dto.getQuantityOfTickets(),
              dto.getQuantityOfTickets())
          .execute();

      // update total ticket
      movieQueries.updateTotalTicketsOfMoviePublic(
          dto.getMoviePublic().getUuid(), dto.getQuantityOfTickets());
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void updatePurchaseDownloads(UUID uuid) {
    PurchaseDto purchase = this.findByUuid(uuid);

    if (Objects.isNull(purchase)) return;

    dsl.update(PURCHASES)
        .set(PURCHASES.DOWNLOADS, purchase.getDownloads() - 1)
        .where(PURCHASES.UUID.eq(CommonUtils.uuidToBytesArray(purchase.getUuid())))
        .execute();
  }
}
