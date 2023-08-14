package org.cinema.models.enums;

public enum FileType {
  IMAGE("IMAGE"),
  PDF("PDF"),
  VIDEO("VIDEO");

  private final String type;

  FileType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
