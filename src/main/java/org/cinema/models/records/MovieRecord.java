package org.cinema.models.records;

import java.time.LocalDateTime;
import java.util.UUID;

public class MovieRecord {
  public record MovieR(
      Long id,
      UUID uuid,
      String name,
      int duration,
      String author,
      LocalDateTime releaseDate,
      int active,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {}
}
