package com.Authentication.domain.vo;

import com.Authentication.application.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailTest {
  @Test
  @DisplayName("Should be able to create a valid email")
  public void should_be_able_to_create_a_valid_email() {
    assertDoesNotThrow(() -> new Email("john.doe@example.com"));
    assertDoesNotThrow(() -> new Email("user123@sub.domain.org"));
    assertDoesNotThrow(() -> new Email("firstname.lastname@company.co"));
    assertDoesNotThrow(() -> new Email("name+tag@example-mail.com"));
    assertDoesNotThrow(() -> new Email("valid_email123@domain.io"));
  }

  @Test
  @DisplayName("Should be able to create a invalid email")
  public void should_not_be_able_to_create_a_invalid_email() {
    assertThrows(ValidationException.class, () -> new Email("plainaddress"));
    assertThrows(ValidationException.class, () -> new Email("@missingusername.com"));
    assertThrows(ValidationException.class, () -> new Email("username@.com"));
    assertThrows(ValidationException.class, () -> new Email("username@domain.c"));
    assertThrows(ValidationException.class, () -> new Email("user@domain,com"));
  }
}
