package org.cinema.models.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.cinema.models.records.MovieRecord.MoviePublicR;

@Data
@Builder
public class MoviePublicDto {
  private UUID uuid;
  private MovieDto movie;
  private UserDto branch;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private int price;
  private int totalTickets;

  public static MoviePublicDto toDto(MoviePublicR moviePublicR) {
    if (Objects.isNull(moviePublicR)) return null;
    return MoviePublicDto.builder()
        .uuid(moviePublicR.uuid())
        .startDate(moviePublicR.startDate())
        .endDate(moviePublicR.endDate())
        .price(moviePublicR.price())
        .totalTickets(moviePublicR.totalTickets())
        .build();
  }
}
