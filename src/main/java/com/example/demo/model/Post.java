package com.example.demo.model;

import com.example.demo.enums.Commendation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "thread_id")
  private Thread thread;

  @Column(name = "content")
  private String content;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
  private Set<Image> postImages;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
  private Set<Video> postVideos;

  @ManyToMany(mappedBy = "savedPosts", fetch = FetchType.EAGER)
  private Set<User> users = new HashSet<>();

  @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
  private Set<UserFilePost> userFilePosts = new HashSet<>();

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @MapKey(name = "commendation")
  private Map<Commendation, PostCommendation> commendations = new HashMap<>();

  @Column(name = "removal_flag")
  private Boolean removalFlag;

  private Map<Commendation,Integer> getCommendationCount(){
    Map<Commendation,Integer> result = new HashMap<>();
    for(Commendation key : commendations.keySet()){
      result.put(key, commendations.get(key).getCount());
    }
    return result;
  }

  @Override
  public String toString() {
    return "Post{" +
        "id=" + id +
        ", user=" + user +
        ", thread=" + thread +
        ", content=" + content +
        ", users=" + users +
        ", userFilePosts=" + userFilePosts +
        ", commendations=" + commendations +
        ", removalFlag=" + removalFlag +
        '}';
  }
}
