package com.example.demo.dto;

import com.example.demo.enums.Commendation;
import com.example.demo.model.Information;
import com.example.demo.model.UserFilePost;
import lombok.Getter;
import lombok.Setter;

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
public class PostDTO extends Information {
  private Long threadId;
  private String author;
  private byte[] content;
  private Set<UserFilePost> userFilePosts;
  private Set<Commendation> commendations;

}
