package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Getter
@Setter
public class ResponseDTO {
  private Integer status;
  private String url;
  private Map<String,Object> data = new HashMap<>();
  private String timestamp;

  @Override
  public String toString() {
    return "ResponseDTO{" +
        "status=" + status +
        ", url='" + url + '\'' +
        ", data=" + data +
        ", timestamp='" + timestamp + '\'' +
        '}';
  }
}
