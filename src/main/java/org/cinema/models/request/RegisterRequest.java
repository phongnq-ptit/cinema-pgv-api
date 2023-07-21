package org.cinema.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cinema.models.dto.UserDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest extends UserDto {
  private String password;
}
