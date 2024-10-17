package com.Authentication.application.repository;

import com.Authentication.domain.entity.Account;

import java.util.Optional;

public interface AccountRepository {
  void save(Account account);
  Optional<Account> findByEmail(String email);
  Optional<Account> findByAccountId(String accountId);
}
