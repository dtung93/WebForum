package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Getter
@Setter
@Entity
@Table(name = "image")
public class Image extends Information implements Serializable {
  @Column(name = "id")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "content")
  private byte[] data;

  @Column(name = "description")
  private String description;

  @Column(name = "file_type")
  private String fileType;

  @Column(name = "file_size")
  private Integer fileSize;

  @Column(name = "removal_flag")
  private Boolean removalFlag;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "post_id")
  private Post post;
}
