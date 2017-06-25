package net.uranus.astra.auth.model.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@Order(value = 101)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity httpSecurity) {
      try {
          httpSecurity.authorizeRequests().antMatchers("/resources/**", "/registration","/registration/**", "/login").permitAll()
          .anyRequest().fullyAuthenticated().and().httpBasic().and().csrf().disable();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}
