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
public class ExceptionDTO {
  private Integer status;
  private String error;
  private String url;
  private String description;
  private String timestamp;

  @Override
  public String toString() {
    return "ExceptionDTO{" +
        "status=" + status +
        ", error='" + error + '\'' +
        ", url='" + url + '\'' +
        ", description='" + description + '\'' +
        ", timestamp='" + timestamp + '\'' +
        '}';
  }
}
