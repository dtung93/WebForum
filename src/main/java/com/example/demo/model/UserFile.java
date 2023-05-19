package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import com.example.demo.enums.FileTag;

import java.util.Arrays;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Entity
@Table(name = "user_file")
@Getter
@Setter
public class UserFile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "file_type")
  private String fileType;

  @Column(name= "tag")
  @Enumerated(EnumType.STRING)
  private FileTag tag;


  @Column(name = "file_size")
  private Integer fileSize;

  @Column(name = "file_data")
  @Lob
  private byte[] fileData;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Override
  public String toString() {
    return "UserFile{" +
        "id=" + id +
        ", fileName='" + fileName + '\'' +
        ", fileType='" + fileType + '\'' +
        ", tag=" + tag +
        ", fileSize=" + fileSize +
        ", fileData=" + Arrays.toString(fileData) +
        ", user=" + user +
        '}';
  }
}
