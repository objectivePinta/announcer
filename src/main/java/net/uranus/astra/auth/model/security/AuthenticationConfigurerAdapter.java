package net.uranus.astra.auth.model.security;

import net.uranus.astra.auth.model.Account;
import net.uranus.astra.auth.model.repository.AccountsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class AuthenticationConfigurerAdapter extends GlobalAuthenticationConfigurerAdapter {

  private final AccountsRepository accountsRepository;
  private final static Logger log = LoggerFactory.getLogger(AuthenticationConfigurerAdapter.class);
  private final AuthProvider authProvider;

  public AuthenticationConfigurerAdapter(AccountsRepository accountsRepository, AuthProvider authProvider) {
    this.accountsRepository = accountsRepository;
    this.authProvider = authProvider;
  }

  @Override
  public void init(AuthenticationManagerBuilder managerBuilder) {
    try {
      managerBuilder.userDetailsService(userDetailsService());
      managerBuilder.authenticationProvider(authProvider);
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Bean
  UserDetailsService userDetailsService() {
    return username -> {
      Account account = accountsRepository.findByUsername(username);
      if (account != null) {
        return User.withDefaultPasswordEncoder().username(account.getUsername()).password(account.getPassword())
            .credentialsExpired(false).accountLocked(false).authorities(AuthorityUtils.createAuthorityList("USER"))
            .disabled(false).accountExpired(false).build();
      }
      else {
        log.error(username + " is not registered");
        throw new RuntimeException(username + " is not registered");
      }
    };
  }

}
