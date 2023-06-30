package com.example.demo.model;

import com.example.demo.enums.FileTag;
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
@Table(name="user_photo")
public class UserPhoto extends Information implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name="file_tag")
  @Enumerated
  private FileTag fileTag;

  @Column(name="file_type")
  private String fileType;

  @Column(name = "file_size")
  private Integer fileSize;

  @Column(name = "removal_flag")
  private Boolean removalFlag;

  @Column(name="view_link")
  private String viewLink;

  @Column(name="download_link")
  private String downloadLink;

  @Column(name="is_profile_photo")
  private Boolean isProfilePhoto;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="user_id")
  private User user;
}
