package com.Authentication.application.usecase;

import com.Authentication.application.dto.CreateAccountInput;
import com.Authentication.application.exception.AccountAlreadyExistsException;
import com.Authentication.application.repository.AccountRepository;
import com.Authentication.domain.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateAccount {
  private final AccountRepository accountRepository;

  @Autowired
  public CreateAccount(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public void execute(CreateAccountInput input) {
    Optional<Account> accountAlreadyExits = this.accountRepository.findByEmail(input.email());
    if(accountAlreadyExits.isPresent()) {
      throw new AccountAlreadyExistsException();
    }
    Account account = new Account(input.firstName(), input.lastName(), input.email(), input.password());
    this.accountRepository.save(account);
  }
}
