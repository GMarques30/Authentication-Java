package com.Authentication.application.usecase;

import com.Authentication.application.dto.LoginAccountInput;
import com.Authentication.application.dto.LoginAccountOutput;
import com.Authentication.application.exception.InvalidCredentials;
import com.Authentication.application.repository.AccountRepository;
import com.Authentication.domain.entity.Account;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class LoginAccount {
  private final AccountRepository accountRepository;

  @Value("${api.security.token.secret}")
  private String secretKey;

  @Autowired
  public LoginAccount(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public LoginAccountOutput execute(LoginAccountInput input) {
    Account account = this.accountRepository.findByEmail(input.email()).orElseThrow(InvalidCredentials::new);
    if(!account.passwordMatches(input.password())) throw new InvalidCredentials();
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    String token =  JWT.create()
            .withIssuer("api-authenticate")
            .withSubject(account.accountId)
            .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
            .sign(algorithm);
    return new LoginAccountOutput(token);
  }
}
