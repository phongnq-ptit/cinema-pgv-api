package org.cinema.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cinema.models.dto.UserDto;
import org.cinema.services.data.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<UserDto> login(@RequestBody LoginPayload loginPayload) {
    UserDto user = authService.login(loginPayload.getEmail(), loginPayload.getPassword());

    return ResponseEntity.ok().body(user);
  }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class LoginPayload {
  private String email;
  private String password;
}
