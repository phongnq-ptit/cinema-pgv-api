package org.cinema.services.data;

import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import org.cinema.models.dto.UserDto;
import org.cinema.models.enums.UserRole;
import org.cinema.models.request.RegisterRequest;
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

  public Response getUsersByRole(UserRole role, String search) {
    return Response.ok()
        .entity(
            new BaseResponse<List<UserDto>>(
                200, "lay ra danh sach user thanh cong", userQueries.findAllByRole(role, search)))
        .build();
  }

  public Response getUsersByUuid(UUID uuid) {
    return Response.ok()
        .entity(
            new BaseResponse<UserDto>(
                200, "lay ra danh sach user thanh cong", userQueries.findByUuid(uuid)))
        .build();
  }

  public Response createUser(RegisterRequest user) {
    userQueries.insert(user);
    return Response.noContent().build();
  }

  public Response updateUser(UUID userUuid, UserDto userUpdate) {
    userQueries.update(userUuid, userUpdate);

    return Response.noContent().build();
  }
}
