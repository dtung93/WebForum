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
  private String id;

  @Column(name = "file_name")
  private String fileName;

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

  @Column(name="web_content_link")
  private String webContentLink;

  @Column(name="thumb_nail_link")
  private String thumbNailLink;

  @Column(name="web_view_link")
  private String webViewLink;

}
