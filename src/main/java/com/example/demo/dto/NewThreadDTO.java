package com.example.demo.dto;

import com.example.demo.enums.ThreadCategory;
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
public class NewThreadDTO {
    private String title;
    private String author;
    private String content;
    private ThreadCategory category;

  @Override
  public String toString() {
    return "NewThreadDTO{" +
        "title='" + title + '\'' +
        ", author='" + author + '\'' +
        ", content='" + content + '\'' +
        ", category='" + category + '\'' +
        '}';
  }
}
