package org.cinema.exception;

import jakarta.ws.rs.core.Response.Status;
import java.io.Serializable;

public class CustomException extends Exception implements Serializable {
  private String code;
  private Status status;

  // DEFINE ERROR CODE
  public static final String USER_NOT_FOUND = "USER_NOT_FOUND";

  // DEFINE MESSAGE ERROR CODE
  public static final String USER_NOT_FOUND_MESSAGE = "Người dùng không tồn tại!";

  public CustomException(String message) {
    super(message);
    this.code = null;
    this.status = null;
  }

  public CustomException(String code, String message) {
    super(message);
    this.code = code;
    this.status = null;
  }

  public CustomException(Status status, String code, String message) {
    super(message);
    this.code = code;
    this.status = status;
  }

  public String getCode() {
    return code;
  }

  public Status getStatus() {
    return status;
  }
}
