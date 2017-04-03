package net.uranus.astra;

import net.uranus.astra.auth.model.User;
import net.uranus.astra.auth.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController("login")
public class LoginController {

  private final UserService userService;

  public LoginController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value=("/login"), method = RequestMethod.HEAD)
  public void login() {
      System.out.println("login");
  }

  @RequestMapping(value = ("/login"), method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
  public String login(Authentication authentication) {
        System.out.println(authentication);
        return "yes";
    }

  @PostConstruct
  public void saveDummyUser() {
    userService.save(User.builder().username("andrei1").password("andrei1").build());
  }
}
