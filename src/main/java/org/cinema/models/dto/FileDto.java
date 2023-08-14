package org.cinema.models.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.cinema.models.enums.FileType;
import org.cinema.models.records.FileRecord.FileR;

@Data
@Builder
public class FileDto {
  private UUID uuid;
  private String fileName;
  private String url;
  private int size;
  private FileType type;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static FileDto toDto(FileR fileR) {
    if (Objects.isNull(fileR)) return null;
    return FileDto.builder()
        .uuid(fileR.uuid())
        .fileName(fileR.fileName())
        .url(fileR.url())
        .size(fileR.size())
        .type(FileType.valueOf(fileR.type()))
        .createdAt(fileR.createdAt())
        .updatedAt(fileR.updatedAt())
        .build();
  }
}
