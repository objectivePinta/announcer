package net.uranus.astra.auth.model.security;

import net.uranus.astra.auth.model.Account;
import net.uranus.astra.auth.model.repository.AccountsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class AuthenticationConfigurerAdapter extends GlobalAuthenticationConfigurerAdapter {

  private final AccountsRepository accountsRepository;

  public AuthenticationConfigurerAdapter(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  @Override
  public void init(AuthenticationManagerBuilder managerBuilder) {
    try {
      managerBuilder.userDetailsService(userDetailsService());
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Bean
  UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountsRepository.findByUsername(username);
        if (account != null) {
          return new User(account.getUsername(), account.getPassword(), true, true, true, true,
              AuthorityUtils.createAuthorityList("USER"));
        }
        else {
          throw new UsernameNotFoundException(username + " is not registered");
        }
      }
    };
  }

}
