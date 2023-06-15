package com.example.demo.model;

import com.example.demo.enums.FileTag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Table(name = "file")
@Entity
@Getter
@Setter
public class File extends Information implements java.io.Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @Column(name = "tag")
  @Enumerated(EnumType.STRING)
  private FileTag tag;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "file_type")
  private String fileType;

  @Column(name = "file_size")
  private Integer fileSize;

  @Column(name = "removal_flag")
  private Boolean removalFlag;

  @OneToMany(mappedBy = "file")
  private Set<UserFilePost> userFilePosts = new HashSet<>();

  @Column(name="web_content_link")
  private String webContentLink;

  @Column(name="thumb_nail_link")
  private String thumbNailLink;

  @Column(name="web_view_link")
  private String webViewLink;
}
