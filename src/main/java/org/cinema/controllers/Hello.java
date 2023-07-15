package org.cinema.controllers;

import org.cinema.services.apis.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class Hello {
@Autowired
EmailService emailService;
  @GetMapping()
  public String hello() {
    emailService.sendMail("test-email");
    return "helllo";
  }
}
