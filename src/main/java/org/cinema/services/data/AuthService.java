package org.cinema.services.data;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.cinema.auth.JwtTokenUtils;
import org.cinema.exception.CustomException;
import org.cinema.models.dto.UserDto;
import org.cinema.models.enums.MailTemplate;
import org.cinema.models.request.LoginRequest;
import org.cinema.models.request.RegisterRequest;
import org.cinema.models.response.BaseResponse;
import org.cinema.models.response.LoginResponse;
import org.cinema.models.response.RefreshTokenResponse;
import org.cinema.queries.UserQueries;
import org.cinema.services.apis.EmailService;
import org.cinema.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private static final long ACCESS_TOKEN_EXPIRY = 43200; // 30 minutes
  private static final long REFRESH_TOKEN_EXPIRY = 43200; // 30 days

  @Autowired private final JwtTokenUtils jwt;
  @Autowired private final UserQueries userQueries;
  @Autowired private final EmailService emailService;

  public AuthService(JwtTokenUtils jwt, UserQueries userQueries, EmailService emailService) {
    this.jwt = jwt;
    this.userQueries = userQueries;
    this.emailService = emailService;
  }

  public Response login(LoginRequest loginRequest) throws CustomException {
    UserDto user =
        userQueries.findByEmailAndPassword(
            loginRequest.getEmail(), CommonUtils.hashPassword(loginRequest.getPassword()));

    if (Objects.isNull(user)) {
      throw new CustomException(
          Status.NOT_FOUND, CustomException.USER_NOT_FOUND, CustomException.USER_NOT_FOUND_MESSAGE);
    }

    if (user.getActive() == 0) {
      throw new CustomException(
          CustomException.ACCOUNT_IS_NOT_VERIFIED, CustomException.ACCOUNT_IS_NOT_VERIFIED_MESSAGE);
    }

    String accessToken = jwt.generateToken(user, ACCESS_TOKEN_EXPIRY);
    String refreshToken = jwt.generateToken(user, REFRESH_TOKEN_EXPIRY);

    return Response.ok()
        .entity(
            new BaseResponse<LoginResponse>(
                200, "login successful", new LoginResponse(accessToken, refreshToken, user)))
        .build();
  }

  public Response register(RegisterRequest newUser) throws CustomException {
    UserDto userExisted = userQueries.findByEmail(newUser.getEmail());
    if (Objects.nonNull(userExisted)) {
      throw new CustomException(CustomException.USER_EXISTED, CustomException.USER_EXISTED_MESSAGE);
    }

    UUID uuidNewUser = UUID.randomUUID();
    newUser.setUuid(uuidNewUser);
    userQueries.insert(newUser);

    Map<String, Object> mail = new HashMap<>();
    mail.put("recipient", newUser.getEmail());
    mail.put("user", newUser);

    emailService.sendMail(MailTemplate.MAIL_VERIFY_ACCOUNT, mail);

    return Response.noContent().build();
  }

  public Response getNewAccessToken(String refreshToken) {
    try {
      UserDto user = jwt.verifyToken(refreshToken);
      if (user == null || StringUtils.isEmpty(user.getUuid().toString())) {
        return Response.serverError().entity("Wrong token!").build();
      }

      String newAccessToken = jwt.generateToken(user, ACCESS_TOKEN_EXPIRY);
      return Response.ok()
          .entity(
              new BaseResponse<>(
                  200, "Refresh accessToken successful", new RefreshTokenResponse(newAccessToken)))
          .build();
    } catch (RuntimeException e) {
      return Response.serverError().entity("Wrong token!").build();
    }
  }

  public Response verifyAccount(UUID userUuid) {
    UserDto user = userQueries.findByUuid(userUuid);

    if (user.getActive() == 1) {
      return Response.ok()
          .entity(new BaseResponse<>(200, "This account had been verify.", null))
          .build();
    }

    user.setActive(1);
    userQueries.update(userUuid, user);

    return Response.ok().entity(new BaseResponse<>(204, "Account verify successful", null)).build();
  }
}
