package org.cinema.services.data;

import jakarta.ws.rs.core.Response;
import java.util.List;
import org.cinema.models.dto.UserDto;
import org.cinema.models.enums.UserRole;
import org.cinema.models.response.BaseResponse;
import org.cinema.queries.UserQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired private final UserQueries userQueries;

  public UserService(UserQueries userQueries) {
    this.userQueries = userQueries;
  }

  public Response getUsersByRole(UserRole role) {
    return Response.ok()
        .entity(
            new BaseResponse<List<UserDto>>(
                200, "lay ra danh sach user thanh cong", userQueries.findAllByRole(role)))
        .build();
  }
}
