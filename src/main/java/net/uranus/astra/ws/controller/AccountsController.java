package net.uranus.astra.ws.controller;

import net.uranus.astra.auth.model.Account;
import net.uranus.astra.auth.model.repository.AccountsRepository;
import net.uranus.astra.ws.controller.requests.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class AccountsController {

  private final AccountsRepository accountsRepository;
  Logger LOGGER = LoggerFactory.getLogger(AccountsController.class);

  public AccountsController(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  @RequestMapping(path = "/login")
  public String login(Principal principal) {
    LOGGER.info("{} just logged in.", principal.getName());
    return "Hello baby";
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
}
