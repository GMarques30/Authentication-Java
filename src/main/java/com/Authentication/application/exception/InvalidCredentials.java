package com.Authentication.application.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentials extends RuntimeException {
  public HttpStatus status;

  public InvalidCredentials() {
    super("Invalid credentials.");
    this.status = HttpStatus.UNAUTHORIZED;

  }
}
