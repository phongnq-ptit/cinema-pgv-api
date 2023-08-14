package org.cinema.models.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDto {
  private UUID uuid;
  private String name;
  private int duration;
  private String author;
  private LocalDateTime releaseDate;
  private List<CategoryDto> categories;
  private List<FileDto> images;
  private FileDto movieFile;
  private int active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
