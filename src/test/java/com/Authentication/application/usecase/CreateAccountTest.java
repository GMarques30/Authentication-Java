package com.Authentication.application.usecase;

import com.Authentication.application.dto.CreateAccountInput;
import com.Authentication.application.exception.AccountAlreadyExistsException;
import com.Authentication.application.repository.AccountRepository;
import com.Authentication.domain.entity.Account;
import com.Authentication.domain.vo.Password;
import com.Authentication.infra.repository.AccountRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateAccountTest {
  private AccountRepository accountRepository;
  private CreateAccount sut;

  @BeforeEach
  public void beforeEach() {
    this.accountRepository = new AccountRepositoryMemory();
    this.sut = new CreateAccount(accountRepository);
  }

  @Test
  @DisplayName("Should be able to create account")
  public void should_be_able_to_create_account() {
    var input = new CreateAccountInput("Fernando", "Silva", "fernando.silva@example.com", "Fernando@123");
    this.sut.execute(input);
    var result = this.accountRepository.findByEmail(input.email());
    assertThat(result).isNotEmpty();
    assertThat(result.get().accountId).isNotEmpty();
    assertThat(result.get().getName()).isEqualTo("Fernando Silva");
    assertThat(result.get().getEmail()).isEqualTo("fernando.silva@example.com");
    assertThat(result.get().getPassword()).isEqualTo(Password.create(input.password()).toString());
    assertThat(result.get().createdAt).isInstanceOf(String.class);
  }

  @Test
  @DisplayName("Should not be able to create an existing account")
  public void should_not_be_able_to_create_an_existing_account() {
    var account = new Account("Fernando", "Silva", "fernando.silva@example.com", "Fernando@123");
    this.accountRepository.save(account);
    assertThrows(AccountAlreadyExistsException.class, () -> this.sut.execute(new CreateAccountInput("Fernando", "Silva", "fernando.silva@example.com", "Fernando@123")));
  }
}
