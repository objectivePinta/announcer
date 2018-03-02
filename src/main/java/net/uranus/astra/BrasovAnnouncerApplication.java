package net.uranus.astra;

import net.uranus.astra.auth.model.Account;
import net.uranus.astra.auth.model.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class BrasovAnnouncerApplication {

  public static void main(String[] args) {
    SpringApplication.run(BrasovAnnouncerApplication.class, args);
  }


  @Bean
  CommandLineRunner init(final AccountsRepository accountRepository) {

    return new CommandLineRunner() {

      @Override
      public void run(String... arg0) throws Exception {
        accountRepository.save(Account.builder().username("andrei").password("andrei").build());

      }

    };

  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000","https://wow-ui.herokuapp.com");
      }
    };
  }

}
