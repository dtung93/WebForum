package com.example.demo.dto;

import com.example.demo.model.Information;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Getter
@Setter
public class GoogleDriveFileDTO extends Information implements Serializable {
  private String id;
  private String name;
  private String size;
  private String webViewContentLink;
  private String webViewLink;
  private String thumbNailLink;
  private boolean shared;
}
