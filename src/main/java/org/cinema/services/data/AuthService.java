package org.cinema.services.data;

import java.util.Objects;
import org.cinema.models.dto.UserDto;
import org.cinema.queries.UserQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired
  private final UserQueries userQueries;

  public AuthService(UserQueries userQueries) {
    this.userQueries = userQueries;
  }

  public UserDto login(String email, String password) {
    UserDto user = userQueries.findByEmailAndPassword(email, password);
    if (Objects.isNull(user)) {
      return null;
    }

    return user;
  }
}
