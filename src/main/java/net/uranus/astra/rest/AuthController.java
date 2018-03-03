package net.uranus.astra.rest;

import net.uranus.astra.auth.model.Account;
import net.uranus.astra.auth.model.repository.AccountsRepository;
import net.uranus.astra.rest.requests.AuthToken;
import net.uranus.astra.rest.requests.User;
import net.uranus.astra.rest.requests.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class AuthController {

  private final AccountsRepository accountsRepository;
  Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

  public AuthController(AccountsRepository accountsRepository) {
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

  @PostMapping(path = "/facebook", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public void facebook(@RequestBody AuthToken accessToken) {
    LOGGER.info(accessToken.getToken());
    Facebook facebook = new FacebookTemplate(accessToken.getToken());
    String[] fields = { "id", "about", "age_range", "birthday", "context", "cover", "currency", "devices", "education",
        "email", "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown", "inspirational_people",
        "installed", "install_type", "is_verified", "languages", "last_name", "link", "locale", "location",
        "meeting_for", "middle_name", "name", "name_format", "political", "quotes", "payment_pricepoints",
        "relationship_status", "religion", "security_settings", "significant_other", "sports", "test_group", "timezone",
        "third_party_id", "updated_time", "verified", "video_upload_limits", "viewer_can_send_gift", "website",
        "work" };
    org.springframework.social.facebook.api.User user = facebook.fetchObject("me",
        org.springframework.social.facebook.api.User.class, fields);
    LOGGER.info(user.getName());
    LOGGER.info(user.getAbout());
    LOGGER.info(user.getEmail());
    LOGGER.info(user.getFirstName());
    LOGGER.info(user.getGender());
    LOGGER.info(user.getId());
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
