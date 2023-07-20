package org.cinema.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.cinema.models.response.ErrorResponse;

@Provider
public class CustomExceptionHandler implements ExceptionMapper<CustomException> {

  @Override
  public Response toResponse(CustomException e) {
    Status status = e.getStatus() != null ? e.getStatus() : Status.BAD_REQUEST;
    return Response.status(status)
        .entity(new ErrorResponse(status, e.getCode(), e.getMessage()))
        .build();
  }
}
