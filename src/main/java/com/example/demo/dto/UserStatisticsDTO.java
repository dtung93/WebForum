package com.example.demo.dto;

import com.example.demo.impl.CustomUserDetails;
import com.example.demo.model.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.Thread;
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
public class UserStatisticsDTO extends CustomUserDetails {
  private Set<Message> messages;
  private Set<UserFile> files;
  private Set<UserFilePost> userFilePosts;
  private Set<Post> savedPosts;
  private Set<Thread> savedThreads;

  public UserStatisticsDTO(User user) {
    super(user);
  }

  @Override
  public String toString() {
    return "UserStatisticsDTO{" +
        "messages=" + messages +
        ", userFilePosts=" + userFilePosts +
        ", savedPosts=" + savedPosts +
        ", savedThreads=" + savedThreads +
        '}';
  }
}
