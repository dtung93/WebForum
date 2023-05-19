package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;

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
public class CustomException extends Throwable {
  private Integer status;
  private String error;
  private String url;
  private String description;
  private String timestamp;
  private String[] errors;
  private String portal;

  public CustomException(Integer status, String error, String url, String description, String timestamp, String[] errors, String portal) {
    this.status = status;
    this.error = error;
    this.url = url;
    this.description = description;
    this.timestamp = timestamp;
    this.errors = errors;
    this.portal = portal;
  }

  @Override
  public String toString() {
    return "CustomException{" +
        "status=" + status +
        ", error='" + error + '\'' +
        ", url='" + url + '\'' +
        ", description='" + description + '\'' +
        ", timestamp='" + timestamp + '\'' +
        ", errors=" + Arrays.toString(errors) +
        ", portal='" + portal + '\'' +
        '}';
  }
}
