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
public class JwtResponseDTO {
  private String accessToken;
  private String tokenType = "Bearer";
}
