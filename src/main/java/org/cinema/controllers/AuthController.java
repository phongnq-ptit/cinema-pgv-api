package org.cinema.controllers;

import jakarta.ws.rs.core.Response;
import java.util.UUID;
import org.cinema.exception.CustomException;
import org.cinema.models.request.LoginRequest;
import org.cinema.models.request.RefreshTokenRequest;
import org.cinema.models.request.RegisterRequest;
import org.cinema.services.data.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public Response login(@RequestBody LoginRequest loginRequest) throws CustomException {
    return authService.login(loginRequest);
  }

  @PostMapping("/register")
  public Response register(@RequestBody RegisterRequest registerRequest) throws CustomException {
    return authService.register(registerRequest);
  }

  @PostMapping("/refresh_token")
  public Response refreshToken(@RequestBody RefreshTokenRequest registerRequest) throws CustomException {
    return authService.getNewAccessToken(registerRequest.getRefreshToken());
  }

  @PostMapping("/verify-account")
  public Response verifyAccount(@RequestParam UUID userUuid) {
    return authService.verifyAccount(userUuid);
  }
}
