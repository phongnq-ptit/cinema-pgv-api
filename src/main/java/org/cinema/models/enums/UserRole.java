package org.cinema.models.enums;

public enum UserRole {
  CLIENT("CLIENT"),
  STAFF("STAFF"),
  BRANCH("BRANCH"),
  ADMIN("ADMIN");

  private final String role;

  UserRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }
}
