package net.uranus.astra.auth.model.security;

import net.uranus.astra.auth.model.Account;
import net.uranus.astra.auth.model.repository.AccountsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import static net.uranus.astra.rest.Constants.FACEBOOK_FIELDS;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
  private static Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);
  private AccountsRepository accountsRepository;

  public AuthenticationFilter(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws ServletException, IOException {
    String xAuth = req.getHeader("X-Authorization");
    if (xAuth != null) {
      Facebook facebook = new FacebookTemplate(xAuth.substring(xAuth.lastIndexOf(":") + 1));
      org.springframework.social.facebook.api.User user = facebook.fetchObject("me",
          org.springframework.social.facebook.api.User.class, FACEBOOK_FIELDS);
      Account account = registerOrRetrieve(user.getEmail());
      List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
      Authentication auth = new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword(),
          authorities);
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    chain.doFilter(req, res);
  }

  private Account registerOrRetrieve(String email) {
    Account account = accountsRepository.findByUsername(email);
    if (account == null) {
      account = Account.builder().username(email).password(UUID.randomUUID().toString()).build();
      accountsRepository.save(account);
      LOG.info("{} registered with facebook", email);
    }
    return account;
  }
}
