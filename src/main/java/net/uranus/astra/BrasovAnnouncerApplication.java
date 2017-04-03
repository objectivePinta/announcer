package net.uranus.astra;

import net.uranus.astra.auth.model.User;
import net.uranus.astra.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BrasovAnnouncerApplication {

  public static void main(String[] args) {
    SpringApplication.run(BrasovAnnouncerApplication.class, args);
  }

}
