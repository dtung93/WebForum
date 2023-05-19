package com.example.demo.model;

import com.example.demo.enums.ThreadCategory;
import lombok.Getter;
import lombok.Setter;

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
@Table(name="thread")
@Getter
@Setter
public class Thread extends Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;
    @Enumerated(EnumType.STRING)
    private ThreadCategory category;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
    private Set<Post> posts = new HashSet<>();

    @JoinColumn(name = "username")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToMany(mappedBy = "savedThreads")
    private Set<User> users = new HashSet<>();

    @Column(name="last_replied")
    private String lastReplied;

    @Column(name="views")
    private Long views;

    @Column(name="removal_flag")
    private Boolean removalFlag;

    @Override
    public String toString() {
        return "Thread{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", category=" + category +
            ", posts=" + posts +
            ", user=" + user +
            ", users=" + users +
            ", lastReplied='" + lastReplied + '\'' +
            ", views=" + views +
            ", removalFlag=" + removalFlag +
            '}';
    }
}
