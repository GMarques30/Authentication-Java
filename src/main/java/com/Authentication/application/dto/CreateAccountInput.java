package com.Authentication.application.dto;

public record CreateAccountInput(String firstName, String lastName, String email, String password) {
}
