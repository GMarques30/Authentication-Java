package com.Authentication.domain.vo;

import com.Authentication.application.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NameTest {
  @Test
  @DisplayName("Should be able to create a valid name")
  public void should_be_able_to_create_a_valid_name() {
    assertDoesNotThrow(() -> new Name("Fernando", "Silva"));
    assertDoesNotThrow(() -> new Name("Maria", "Campos"));
  }

  @Test
  @DisplayName("Should not be able to create a invalid names")
  void should_not_be_able_to_create_a_invalid_names() {
    assertThrows(ValidationException.class, () -> new Name("", "Silva"));
    assertThrows(ValidationException.class, () -> new Name("Fernando", ""));
    assertThrows(ValidationException.class, () -> new Name("Fernando123", "Silva"));
    assertThrows(ValidationException.class, () -> new Name("Fernando", "Si1va"));
    assertThrows(ValidationException.class, () -> new Name("   ", "Silva"));
    assertThrows(ValidationException.class, () -> new Name("Fernando", "   "));
  }
}
