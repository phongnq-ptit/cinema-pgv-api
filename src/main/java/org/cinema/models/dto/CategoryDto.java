package org.cinema.models.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.cinema.models.records.CategoryRecord.CategoryR;

@Data
@Builder
public class CategoryDto {
  private UUID uuid;
  private String name;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static CategoryDto toDto(CategoryR category) {
    if (Objects.isNull(category)) return null;
    return CategoryDto.builder()
        .uuid(category.uuid())
        .name(category.name())
        .description(category.description())
        .createdAt(category.createdAt())
        .updatedAt(category.updatedAt())
        .build();
  }
}
