package com.Authentication.domain.vo;

import com.Authentication.application.exception.ValidationException;

public class Email {
  private final String email;

  public Email(String email) {
    if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) throw new ValidationException("Invalid email");
    this.email = email;
  }

  @Override
  public String toString() {
    return this.email;
  }
}
