package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Entity
@Table( name = "user_file_post")
@Getter
@Setter
public class UserFilePost {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "file_id")
  private File file;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @Override
  public String toString() {
    return "UserFilePost{" +
        "id=" + id +
        ", user=" + user +
        ", file=" + file +
        ", post=" + post +
        '}';
  }
}
