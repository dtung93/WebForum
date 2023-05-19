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
@Table(name = "user_commendation")
@Getter
@Setter
public class UserCommendation extends Information {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name="commendation_type")
  private String commendationType;

  @Column(name = "commendation")
  @Enumerated(EnumType.STRING)
  private Commendation commendation;

  @Column(name = "count")
  private Integer count;

  @Override
  public String toString() {
    return "UserCommendation{" +
        "id=" + id +
        ", user=" + user +
        ", commendationType='" + commendationType + '\'' +
        ", commendation=" + commendation +
        ", count=" + count +
        '}';
  }
}
