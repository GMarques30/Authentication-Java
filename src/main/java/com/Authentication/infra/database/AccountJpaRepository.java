package com.Authentication.infra.database;

import com.Authentication.infra.database.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface AccountJpaRepository extends JpaRepository<AccountModel, String> {
  Optional<AccountModel> findByEmail(String email);
}
