package com.example.demo.model;

import com.example.demo.enums.Commendation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Entity
@Table(name = "post")
@Getter
@Setter
public class Post extends Information implements java.io.Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "thread_id")
  private Thread thread;

  @Column(name="content")
  @Lob
  private byte[] content;

  @ManyToMany(mappedBy = "savedPosts")
  private Set<User> users = new HashSet<>();

  @OneToMany(mappedBy = "post")
  private Set<UserFilePost> userFilePosts = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "post_commendations",
      joinColumns = @JoinColumn(name = "post_id")
  )
  @MapKeyColumn(name = "commendation_type")
  @Column(name = "count")
  private Map<Commendation,Integer> commendations = new HashMap<>();

  @Column(name="removal_flag")
  private Boolean removalFlag;


  public void addCommendationCount(Commendation commendation, int count) {
    commendations.put(commendation, count);
  }

  public int getCommendationCount(Commendation commendation) {
    return commendations.getOrDefault(commendation, 0);
  }

  @Override
  public String toString() {
    return "Post{" +
        "id=" + id +
        ", user=" + user +
        ", thread=" + thread +
        ", content=" + Arrays.toString(content) +
        ", users=" + users +
        ", userFilePosts=" + userFilePosts +
        ", commendations=" + commendations +
        ", removalFlag=" + removalFlag +
        '}';
  }
}
