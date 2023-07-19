package org.cinema.services.data;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Objects;
import org.cinema.auth.JwtTokenUtils;
import org.cinema.models.dto.UserDto;
import org.cinema.models.request.LoginRequest;
import org.cinema.models.response.BaseResponse;
import org.cinema.models.response.LoginResponse;
import org.cinema.queries.UserQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private static final long ACCESS_TOKEN_EXPIRY = 30; // 30 minutes
  private static final long REFRESH_TOKEN_EXPIRY = 43200; // 30 days

  @Autowired
  private final JwtTokenUtils jwt;
  @Autowired
  private final UserQueries userQueries;

  public AuthService(JwtTokenUtils jwt, UserQueries userQueries) {
    this.jwt = jwt;
    this.userQueries = userQueries;
  }

  public Response login(LoginRequest loginRequest) {
    UserDto user = userQueries.findByEmailAndPassword(loginRequest.getEmail(),
        loginRequest.getPassword());
    if (Objects.isNull(user)) {
      return Response.status(Status.NOT_FOUND)
          .entity(new BaseResponse<>(404, "user do not exsited", null))
          .build();
    }

    String accessToken = jwt.generateToken(user, ACCESS_TOKEN_EXPIRY);
    String refreshToken = jwt.generateToken(user, REFRESH_TOKEN_EXPIRY);

    return Response.ok()
        .entity(new BaseResponse<LoginResponse>(
            200,
            "login successful",
            new LoginResponse(accessToken, refreshToken)))
        .build();
  }
}
