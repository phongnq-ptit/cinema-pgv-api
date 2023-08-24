package org.cinema.models.records;

import java.time.LocalDateTime;
import java.util.UUID;

public class PurchaseRecord {
  public record PurchaseR(
      Long id,
      UUID uuid,
      UUID userUuid,
      UUID moviePublicUuid,
      int quantityOfTickets,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      int downloads) {}
}
