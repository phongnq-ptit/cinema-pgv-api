package org.cinema.controllers;

import jakarta.ws.rs.core.Response;
import java.util.UUID;
import org.cinema.models.dto.UserDto;
import org.cinema.models.enums.UserRole;
import org.cinema.models.request.RegisterRequest;
import org.cinema.services.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/{userUuid}")
  public Response getUser(@PathVariable("userUuid") UUID uuid) {
    return userService.getUsersByUuid(uuid);
  }

  @PostMapping()
  public Response createUser(@RequestBody RegisterRequest newUser) {
    return userService.createUser(newUser);
  }

  @PatchMapping("/{userUuid}")
  public Response updateUser(@PathVariable("userUuid") UUID uuid, @RequestBody UserDto dto) {
    return userService.updateUser(uuid, dto);
  }
}
