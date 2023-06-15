package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Getter
@Setter
@NoArgsConstructor
public class EmailDTO implements Serializable{
  private String username;
  private String newPassword;
  private String email;
  private String token;

}
