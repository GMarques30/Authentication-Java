package com.Authentication.application.usecase;

import com.Authentication.application.dto.LoginAccountInput;
import com.Authentication.application.dto.LoginAccountOutput;
import com.Authentication.application.exception.InvalidCredentials;
import com.Authentication.application.repository.AccountRepository;
import com.Authentication.domain.entity.Account;
import com.Authentication.infra.repository.AccountRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginAccountTest {

  private AccountRepository accountRepository;
  @InjectMocks
  private LoginAccount sut;

  @BeforeEach
  public void beforeEach() {
    try {
      this.accountRepository = new AccountRepositoryMemory();
      this.sut = new LoginAccount(accountRepository);
      MockitoAnnotations.openMocks(this);
      var secretKeyField = LoginAccount.class.getDeclaredField("secretKey");
      secretKeyField.setAccessible(true);
      secretKeyField.set(sut, "test-secret-key");
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Could not set field: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Should be able to return a token when logging in with a valid email and password")
  public void should_be_able_to_return_a_token_when_logging_in_with_a_valid_email_and_password() {
    Account account = new Account("Fernando", "Silva", "fernando.silva@example.com", "Fernando@123");
    this.accountRepository.save(account);
    LoginAccountInput input = new LoginAccountInput(account.getEmail(), "Fernando@123");
    LoginAccountOutput output = this.sut.execute(input);
    assertThat(output.token()).isNotEmpty();
  }

  @Test
  @DisplayName("Should not be able to return a token with email unregistered")
  public void should_not_be_able_to_return_a_token_with_email_unregistered() {
    LoginAccountInput input = new LoginAccountInput("invalid@example.com", "Fernando@123");
    assertThrows(InvalidCredentials.class, () -> this.sut.execute(input));
  }

  @Test
  @DisplayName("Should not be able to return a token with password invalid")
  public void should_not_be_able_to_return_a_token_with_password_invalid() {
    Account account = new Account("Fernando", "Silva", "fernando.silva@example.com", "Fernando@123");
    this.accountRepository.save(account);
    LoginAccountInput input = new LoginAccountInput(account.getEmail(), "Invalid@123");
    assertThrows(InvalidCredentials.class, () -> this.sut.execute(input));
  }
}
