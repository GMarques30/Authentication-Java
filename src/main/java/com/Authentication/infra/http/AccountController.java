package com.Authentication.infra.http;

import com.Authentication.application.dto.CreateAccountInput;
import com.Authentication.application.dto.LoginAccountInput;
import com.Authentication.application.dto.LoginAccountOutput;
import com.Authentication.application.usecase.CreateAccount;
import com.Authentication.application.usecase.LoginAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AccountController {
  private final CreateAccount createAccount;
  private final LoginAccount loginAccount;

  @Autowired
  public AccountController(CreateAccount createAccount, LoginAccount loginAccount) {
    this.createAccount = createAccount;
    this.loginAccount = loginAccount;
  }

  @PostMapping("/register")
  public ResponseEntity<Void> createAccount(@RequestBody CreateAccountInput input) {
    this.createAccount.execute(input);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  public ResponseEntity<LoginAccountOutput> loginAccount(@RequestBody LoginAccountInput input) {
    LoginAccountOutput output = this.loginAccount.execute(input);
    return ResponseEntity.status(HttpStatus.OK).body(output);
  }
}
