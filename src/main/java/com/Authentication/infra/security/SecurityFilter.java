package com.Authentication.infra.security;

import com.Authentication.application.repository.AccountRepository;
import com.Authentication.domain.entity.Account;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  AccountRepository accountRepository;

  @Value("${api.security.token.secret}")
  private String secretKey;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if(checkIfEndpointIsNotPublic(request)) {
      String token = this.recoveryToken(request);
      if(token == null) throw new RuntimeException("The token is missing.");
      String accountId = this.validateToken(token);
      Account account = this.accountRepository.findByAccountId(accountId).get();
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(account, null);
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    filterChain.doFilter(request, response);
  }

  private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    return !Arrays.asList(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
  }

  private String recoveryToken(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    if(header == null) return null;
    return header.replace("Bearer ", "");
  }

  private String validateToken(String token) {
      Algorithm algorithm = Algorithm.HMAC256(secretKey);
      return JWT.require(algorithm)
              .withIssuer("api-authenticate")
              .build()
              .verify(token)
              .getSubject();
  }
}
