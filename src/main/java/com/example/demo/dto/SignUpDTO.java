package com.example.demo.dto;

import com.example.demo.model.UserCommendation;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Getter
@Setter
public class SignUpDTO {
  private String username;
  private String email;
  private String password;
  private String phone=" ";
  private String address=" ";
  private String badge=" ";


  @Override
  public String toString() {
    return "SignUpDTO{" +
        "username='" + username + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", phone='" + phone + '\'' +
        ", address='" + address + '\'' +
        ", badge='" + badge + '\'' +
        '}';
  }
}
