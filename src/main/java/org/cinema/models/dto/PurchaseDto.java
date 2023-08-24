package org.cinema.models.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.cinema.models.records.PurchaseRecord.PurchaseR;

@Builder
@Data
public class PurchaseDto {
  private UUID uuid;
  private UserDto user;
  private MoviePublicDto moviePublic;
  private int quantityOfTickets;
  private int downloads;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static PurchaseDto toDto(PurchaseR purchaseR) {
    if (Objects.isNull(purchaseR)) {
      return null;
    }

    return PurchaseDto.builder()
        .uuid(purchaseR.uuid())
        .quantityOfTickets(purchaseR.quantityOfTickets())
        .downloads(purchaseR.downloads())
        .createdAt(purchaseR.createdAt())
        .updatedAt(purchaseR.updatedAt())
        .build();
  }
}
