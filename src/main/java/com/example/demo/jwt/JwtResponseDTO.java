package com.example.demo.jwt;

import com.example.demo.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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
public class JwtResponseDTO {
  private String username;
  private Set<Role> roles;
  private Date expiration;
}
