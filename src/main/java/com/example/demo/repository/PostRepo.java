package com.example.demo.repository;

import com.example.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

  @Query(nativeQuery = true, value = "SELECT * FROM POST p where p.thread_id =:threadId")
  List<Post> getPostByThreadId(@Param("threadId") Long threadId);

  Optional<Post> findById(Long id);
  Post save (Post post);
}
