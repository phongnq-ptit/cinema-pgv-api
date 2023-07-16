package org.cinema.models.records;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserRecord {

  public record UserR(
      Long id,
      UUID uuid,
      String role,
      String userName,
      String email,
      String password,
      String address,
      UUID cinemaId,
      Integer active,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {}
}
