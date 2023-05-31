package com.example.demo.repository;

import com.example.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Repository
public interface PostRepo extends JpaRepository<Post,Long> {

  Optional<Post> findById(Long id);
  Post save (Post post);
}
