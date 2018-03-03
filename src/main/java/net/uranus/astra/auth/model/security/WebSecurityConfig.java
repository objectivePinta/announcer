package net.uranus.astra.auth.model.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity httpSecurity) {
    try {
      httpSecurity.cors().and().authorizeRequests()
          .antMatchers("/facebook", "/facebook/**/  ", "/resources/**", "/registration", "/registration/**", "/login")
          .permitAll().anyRequest().fullyAuthenticated().and().httpBasic().realmName("WOWrealm").and().csrf().disable();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
