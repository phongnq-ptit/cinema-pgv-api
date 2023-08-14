package org.cinema.models.records;

import java.util.UUID;

public class MovieCategory {
  public record MovieCategoryR(Long id, UUID uuid, UUID movieUuid, UUID categoryUuid) {}
}
