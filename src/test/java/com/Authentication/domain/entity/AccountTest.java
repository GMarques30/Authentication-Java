package com.Authentication.domain.entity;

import com.Authentication.domain.vo.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {
  @Test
  @DisplayName("Should be able to create a account entity")
  public void should_be_able_to_create_a_account_entity() {
    var account = new Account("Fernando", "Silva", "fernando.silva@example.com", "Fernando@123");
    assertThat(account.accountId).isNotEmpty();
    assertThat(account.getName()).isEqualTo("Fernando Silva");
    assertThat(account.getEmail()).isEqualTo("fernando.silva@example.com");
    assertThat(account.getPassword()).isEqualTo(Password.create("Fernando@123").toString());
    assertThat(account.createdAt).isInstanceOf(String.class);
  }
}
