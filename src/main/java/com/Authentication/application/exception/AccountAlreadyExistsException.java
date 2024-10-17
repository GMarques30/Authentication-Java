package com.Authentication.application.exception;

import org.springframework.http.HttpStatus;

public class AccountAlreadyExistsException extends RuntimeException {
  public HttpStatus status;

  public AccountAlreadyExistsException() {
    super("The account already exists in the system.");
    this.status = HttpStatus.CONFLICT;
  }

}
