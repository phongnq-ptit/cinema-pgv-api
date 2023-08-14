package org.cinema.models.records;

import java.time.LocalDateTime;
import java.util.UUID;

public class CategoryRecord {
  public record CategoryR(
      Long id,
      UUID uuid,
      String name,
      String description,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {}
}
