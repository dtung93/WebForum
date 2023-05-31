package com.example.demo.dto;

import com.example.demo.impl.CustomUserDetails;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserCommendation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
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
public class UserInfoDTO implements Serializable {
  private String email;
  private String phone;
  private String address;
  private String badge;
  private Set<UserCommendation> commendations;
  public String accessToken;

  @Override
  public String toString() {
    return "UserInfoDTO{" +
        "email='" + email + '\'' +
        ", phone='" + phone + '\'' +
        ", address='" + address + '\'' +
        ", badge='" + badge + '\'' +
        ", commendations=" + commendations +
        ", accessToken='" + accessToken + '\'' +
        '}';
  }
}
