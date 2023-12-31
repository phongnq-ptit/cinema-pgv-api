package org.cinema.exception;

import jakarta.ws.rs.core.Response.Status;
import java.io.Serializable;

public class CustomException extends Exception implements Serializable {
  private String code;
  private Status status;

  // DEFINE ERROR CODE
  public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
  public static final String USER_EXISTED = "USER_EXISTED";
  public static final String ACCOUNT_IS_NOT_VERIFIED = "ACCOUNT_IS_NOT_VERIFIED";

  // DEFINE MESSAGE ERROR CODE
  public static final String USER_NOT_FOUND_MESSAGE = "Người dùng không tồn tại!";
  public static final String USER_EXISTED_MESSAGE = "Người dùng đã tồn tại";
  public static final String ACCOUNT_IS_NOT_VERIFIED_MESSAGE = "Tài khoản người dùng chưa được xác thực!";

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
