package com.Authentication.application.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {
  public HttpStatus status;

  public ValidationException(String message) {
    super(message);
    this.status = HttpStatus.BAD_REQUEST;
  }
}
