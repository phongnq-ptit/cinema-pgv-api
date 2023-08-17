package org.cinema.models.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.cinema.models.records.MovieRecord.MovieR;

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

  public static MovieDto toDto(MovieR movieR) {
    if (Objects.isNull(movieR)) return null;
    return MovieDto.builder()
        .uuid(movieR.uuid())
        .name(movieR.name())
        .duration(movieR.duration())
        .author(movieR.author())
        .releaseDate(movieR.releaseDate())
        .active(movieR.active())
        .createdAt(movieR.createdAt())
        .updatedAt(movieR.updatedAt())
        .build();
  }
}
