package com.Authentication.domain.vo;

import com.Authentication.application.exception.ValidationException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {
  private final String password;

  private Password(String password) {
    this.password = password;
  }

  private static String hashPassword(String password) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(password.getBytes());
      StringBuilder hexString = new StringBuilder(2 * hash.length);
      for (byte b : hash) {
        hexString.append(String.format("%02x", b));
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("SHA-256 algorithm not found", e);
    }
  }

  public boolean passwordMatches(String password) {
    return this.password.equals(hashPassword(password));
  }

  public static Password create(String password) {
    if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$")) {
      throw new ValidationException("Invalid password");
    }
    String encryptedPassword = hashPassword(password);
    return new Password(encryptedPassword);
  }

  public static Password restore(String password) {
    return new Password(password);
  }

  @Override
  public String toString() {
    return this.password;
  }
}
