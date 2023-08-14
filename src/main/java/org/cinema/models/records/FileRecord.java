package org.cinema.models.records;

import java.time.LocalDateTime;
import java.util.UUID;

public class FileRecord {
  public record FileR(
      Long id,
      UUID uuid,
      String fileName,
      String url,
      int size,
      String type,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {}
}
