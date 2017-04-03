package net.uranus.astra.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

  private final AuthenticationManager authenticationManager;

  private final UserDetailsServiceImpl userDetailsService;
  private static final Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);

  public SecurityService(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
  }

  public String findLoggedInUsername() {
    Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
    if (userDetails instanceof UserDetails) {
      return (((UserDetails) userDetails).getUsername());
    }
    return null;
  }

  public void autologin(String username, String password) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
        password, userDetails.getAuthorities());
    authenticationManager.authenticate(authenticationToken);

    if (authenticationToken.isAuthenticated()) {
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      LOGGER.info("Autologin for {} was successfull", username);
    }

  }
}
