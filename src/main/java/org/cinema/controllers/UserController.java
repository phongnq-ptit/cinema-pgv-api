package org.cinema.controllers;

import jakarta.ws.rs.core.Response;
import org.cinema.models.enums.UserRole;
import org.cinema.services.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public Response getUserByRole(@RequestParam(defaultValue = "CLIENT") UserRole role) {
    return userService.getUsersByRole(role);
  }
}
