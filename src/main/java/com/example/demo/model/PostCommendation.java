package com.example.demo.model;

import com.example.demo.enums.Commendation;
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
@Table(name="post_commendation")
@Getter
@Setter
public class PostCommendation extends Information {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;


  @Enumerated(EnumType.STRING)
  private Commendation commendation;

  @Column(name = "count")
  private Integer count;

  @Override
  public String toString() {
    return "PostCommendation{" +
        "id=" + id +
        ", post=" + post +
        ", commendation=" + commendation +
        ", count=" + count +
        '}';
  }
}
