package com.example.demo.model;

import com.example.demo.enums.ThreadCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Entity
@Table(name = "thread")
@Getter
@Setter
public class Thread extends Information implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title")
  private String title;


  @Enumerated(EnumType.STRING)
  private ThreadCategory category;

  @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Post> posts = new HashSet<>();

  @JoinColumn(name = "user_id")
  @ManyToOne(fetch = FetchType.EAGER)
  private User user;

  @ManyToMany(mappedBy = "savedThreads", fetch = FetchType.EAGER)
  private Set<User> users = new HashSet<>();

  @Column(name = "last_replied")
  private String lastReplied;

  @Column(name = "views")
  private Long views;

  @Column(name = "removal_flag")
  private Boolean removalFlag;


  @Override
  public String toString() {
    return "Thread{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", category=" + category +
        ", lastReplied='" + lastReplied + '\'' +
        ", views=" + views +
        ", removalFlag=" + removalFlag +
        ", createdBy='" + createdBy + '\'' +
        ", createdDate=" + createdDate +
        ", updatedBy='" + updatedBy + '\'' +
        ", updatedDate=" + updatedDate +
        '}';
  }
}
