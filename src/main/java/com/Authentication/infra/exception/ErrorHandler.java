package com.Authentication.infra.exception;

import com.Authentication.application.exception.AccountAlreadyExistsException;
import com.Authentication.application.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
  @ExceptionHandler(AccountAlreadyExistsException.class)
  public ResponseEntity<String> handleAccountAlreadyExistsException(AccountAlreadyExistsException e) {
    return ResponseEntity.status(e.status).body(e.getMessage());
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<String> handleValidationException(ValidationException e) {
    return ResponseEntity.status(e.status).body(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred on the server");
  }
}
