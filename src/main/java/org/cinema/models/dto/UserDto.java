package org.cinema.models.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cinema.models.enums.UserRole;
import org.cinema.models.records.UserRecord.UserR;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private UUID uuid;
  private UserRole role;
  private String userName;
  private String email;
  private String address;
  private UUID cinemaId;
  private Integer active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static UserDto toDto(UserR user) {
    if (Objects.isNull(user)) return null;
    return new UserDto(user.uuid(), UserRole.valueOf(user.role()), user.userName(),
        user.email(), user.address(), user.cinemaId(), user.active(), user.createdAt(),
        user.updatedAt());
  }
}
