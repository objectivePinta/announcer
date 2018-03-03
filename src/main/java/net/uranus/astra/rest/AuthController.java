package net.uranus.astra.rest;

import net.uranus.astra.auth.model.Account;
import net.uranus.astra.auth.model.repository.AccountsRepository;
import net.uranus.astra.auth.model.security.AuthProvider;
import net.uranus.astra.rest.requests.AuthToken;
import net.uranus.astra.rest.requests.User;
import net.uranus.astra.rest.requests.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.uranus.astra.rest.Constants.FACEBOOK_FIELDS;

@RestController
public class AuthController {

  private final AccountsRepository accountsRepository;
  private final AuthProvider authProvider;
  Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

  public AuthController(AccountsRepository accountsRepository, AuthProvider authProvider) {
    this.accountsRepository = accountsRepository;
    this.authProvider = authProvider;
  }

  @RequestMapping(path = "/login")
  public String login(Principal principal) {
    LOGGER.info("{} just logged in.", principal.getName());
    return "Hello baby";
  }

  @PostMapping(path = "/loginWithFacebook")
  public ResponseEntity<User> loginWithFacebook(Principal principal) {
    return ResponseEntity.ok(User.builder().username(principal.getName()).build());
  }

  @PostMapping(path = "/registration", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity registerUser(@RequestBody UserData userData) {
    if (accountsRepository.findByUsername(userData.getUsername()) == null) {
      accountsRepository
          .save(Account.builder().username(userData.getUsername()).password(userData.getPassword()).build());
      accountsRepository.findAll().forEach(account -> LOGGER.info(account.toString()));
      return ResponseEntity.ok().build();
    }
    else {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(path = "/facebook", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public void facebook(@RequestBody AuthToken accessToken) {
    LOGGER.info(accessToken.getToken());
    Facebook facebook = new FacebookTemplate(accessToken.getToken());
    org.springframework.social.facebook.api.User user = facebook.fetchObject("me",
        org.springframework.social.facebook.api.User.class, FACEBOOK_FIELDS);
    if (accountsRepository.findByUsername(user.getEmail()) == null) {
      accountsRepository
          .save(Account.builder().username(user.getEmail()).password(UUID.randomUUID().toString()).build());
      LOGGER.info("{} registered with facebook", user.getEmail());
    }
  }

  @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<User> get(Principal principal) {
    LOGGER.info("/user was called");
    if (principal != null) {
      return ResponseEntity.ok(User.builder().username(principal.getName()).build());
    }
    else {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @GetMapping(path="/privacy")
  String privacyPollicy() {
    return "We just create an account for this app, using the email provided by facebook";
  }
}
