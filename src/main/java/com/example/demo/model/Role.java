package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
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
@Table(name = "role")
public class Role implements GrantedAuthority {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="name",unique = true)
  private String name;

  @Override
  public String getAuthority(){
    return name;
  }

  @ManyToMany(mappedBy = "roles")
  private Set<User> users = new HashSet<>();

//  @Override
//  public String toString() {
//    return "Role{" +
//        "id=" + id +
//        ", name='" + name + '\'' +
//        ", users=" + users +
//        '}';
//  }
}
