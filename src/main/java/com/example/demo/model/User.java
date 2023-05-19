package com.example.demo.model;

import com.example.demo.enums.Badge;
import com.example.demo.enums.Commendation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends Information implements java.io.Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",nullable = false)
    private String username;

    @Column(name= "password",nullable = false)
    private String password;

    @Column(name="email",nullable = false)
    private String email;

    @Column(name= "phone",nullable = false)
    private String phone;

    @Column(name="address")
    private String address;

    @OneToMany(mappedBy = "user")
    private Set<UserFilePost> userFilePosts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Message> messages = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_saved_post",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="post_id")
    )
    private Set<Post> savedPosts = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_saved_thread",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="thread_id")
    )
    private Set<Post> savedThreads = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCommendation> commendations = new HashSet<>();

    public void addCommendation(Commendation commendation, int count) {
        UserCommendation userCommendation = new UserCommendation();
        userCommendation.setUser(this);
        userCommendation.setCommendation(commendation);
        userCommendation.setCount(count);
        commendations.add(userCommendation);
    }

    public void removeCommendation(Commendation commendation) {
        commendations.removeIf(userCommendation -> userCommendation.getCommendation() == commendation);
    }

    public int getCommendationCount(Commendation commendation) {
        for (UserCommendation userCommendation : commendations) {
            if (userCommendation.getCommendation() == commendation) {
                return userCommendation.getCount();
            }
        }
        return 0;
    }

    @Column(name="badge")
    private String badge;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles = new HashSet <>();

    @OneToMany(mappedBy = "user")
    private Set<UserFile> files = new HashSet<>();

    @Column(name="activate_token")
    private String activateToken;

    @Column(name="change_password_token")
    private String changePasswordToken;

    @Column(name="removal_flag")
    private Boolean removalFlag;

    @Column(name="login_attempt")
    private int loginAttempt;
    public void increaseLoginAttempt(){
        loginAttempt++;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", address='" + address + '\'' +
            ", userFilePosts=" + userFilePosts +
            ", messages=" + messages +
            ", savedPosts=" + savedPosts +
            ", savedThreads=" + savedThreads +
            ", commendations=" + commendations +
            ", badge=" + badge +
            ", roles=" + roles +
            ", files=" + files +
            ", activateToken='" + activateToken + '\'' +
            ", changePasswordToken='" + changePasswordToken + '\'' +
            ", removalFlag=" + removalFlag +
            ", loginAttempt=" + loginAttempt +
            '}';
    }
}
