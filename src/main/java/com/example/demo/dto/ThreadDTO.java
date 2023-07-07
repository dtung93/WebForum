package com.example.demo.dto;

import com.example.demo.enums.ThreadCategory;
import com.example.demo.model.Information;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Getter
@Setter
public class ThreadDTO extends Information {
  private Long id;
  private String title;

  private ThreadCategory category;

  private String author;
  private Integer views;
  private Integer replies;

}
