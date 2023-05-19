package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Table(name = "message")
@Entity
@Getter
@Setter
public class Message extends Information implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name="recipient")
  private String recipient;

  @Column(name="content")
  @Lob
  private byte[] content;


  @OneToMany(mappedBy = "message")
  private Set<MessageFile> files = new HashSet<>();


  @Override
  public String toString() {
    return "Message{" +
        "id=" + id +
        ", user=" + user +
        ", recipient='" + recipient + '\'' +
        ", content=" + Arrays.toString(content) +
        ", files=" + files +
        '}';
  }


}
