package net.uranus.astra.auth.model;

import lombok.Builder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@Builder
public class Account {
  private Long id;
  private String username;
  private String password;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}