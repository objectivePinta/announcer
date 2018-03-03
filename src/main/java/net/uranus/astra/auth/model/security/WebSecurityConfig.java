package net.uranus.astra.auth.model.security;

import net.uranus.astra.auth.model.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity httpSecurity) {
    try {
      httpSecurity.cors().and().authorizeRequests()
          .antMatchers("/facebook", "/privacy", "/facebook/**/  ", "/resources/**", "/registration", "/registration/**",
              "/login")
          .permitAll().anyRequest().fullyAuthenticated().and().httpBasic().realmName("WOWrealm").and()
          .addFilterBefore(new AuthenticationFilter(accountsRepository), BasicAuthenticationFilter.class).csrf()
          .disable();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Autowired
  AccountsRepository accountsRepository;
}
