package com.example.demo.dto;

import com.example.demo.enums.Commendation;
import com.example.demo.model.Information;
import com.example.demo.model.User;
import com.example.demo.model.UserFilePost;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
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
  private String userRole;
  private String content;
  private Set<UserFilePost> userFilePosts = new HashSet<>();
  private Set<Commendation> commendations = new HashSet<>();

}
