package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Getter
@Setter
public class AuthenticateDTO {
  private String username;
  private String email;
  private String password;

  public AuthenticateDTO(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  @Override
  public String toString() {
    return "SignUpDTO{" +
        "username='" + username + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
