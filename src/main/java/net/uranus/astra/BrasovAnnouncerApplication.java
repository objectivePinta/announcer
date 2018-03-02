package net.uranus.astra;

import net.uranus.astra.auth.model.Account;
import net.uranus.astra.auth.model.repository.AccountsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = { "net.uranus.astra" })
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
        accountRepository.findAll().stream().forEach(t -> System.out.println(t.getUsername()));
      }

    };

  }


  @Bean
  public FilterRegistrationBean simpleCorsFilter() {
    List<String> allowedOrigins = new ArrayList<>();
    allowedOrigins.add("http://localhost:3000");
    allowedOrigins.add( "https://wow-ui.herokuapp.com");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(allowedOrigins);
    config.setAllowedMethods(Collections.singletonList("*"));
    config.setAllowedHeaders(Collections.singletonList("*"));
    source.registerCorsConfiguration("/**", config);
    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }

}
