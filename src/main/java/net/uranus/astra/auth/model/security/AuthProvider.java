package net.uranus.astra.auth.model.security;

import net.uranus.astra.auth.model.repository.AccountsRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuthProvider implements AuthenticationProvider {
  private final AccountsRepository accountsRepository;

  public AuthProvider(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String name = authentication.getName();
    accountsRepository.findByUsername(name);
    String password = authentication.getCredentials().toString();

    return new UsernamePasswordAuthenticationToken(name, password, authentication.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}