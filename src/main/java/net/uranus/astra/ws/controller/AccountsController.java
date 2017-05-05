package net.uranus.astra.ws.controller;

import net.uranus.astra.auth.model.Account;
import net.uranus.astra.auth.model.repository.AccountsRepository;
import net.uranus.astra.ws.controller.requests.UserData;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AccountsController {

  private final AccountsRepository accountsRepository;

  public AccountsController(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  @RequestMapping(path = "/login")
  public String login(Principal principal) {
    System.out.println(principal);
    System.out.println("nothing good came from");
    return "Hello baby";
  }

  @PostMapping(path = "/registration", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public void registerUser(@RequestBody UserData userData) {
    System.out.println("registering this crap");
    accountsRepository.save(Account.builder().username(userData.getUsername()).password(userData.getPassword()).build());
  }
}
