package com.example.demo.repository;

import com.example.demo.model.Post;
import com.example.demo.model.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
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

  @Query(nativeQuery = true, value= "SELECT t.* FROM post p inner join thread t on p.thread_id = t.id AND p.id = :postId")
  Thread getThreadIdByPost(Long postId);

  @Query(nativeQuery = true, value = "SELECT * FROM POST p where p.thread_id =:threadId and p.removal_flag = 0")
  List<Post> getPostByThreadId(@Param("threadId") Long threadId);

  @Query("SELECT p FROM Post p where p.thread.id =:threadId AND p.removalFlag = false")
  Page<Post> getPostsByThreadId (@Param("threadId") Long threadId, Pageable pageable);

  @Query("SELECT p FROM Post p INNER JOIN User u on u.id = p.user.id where p.user.id = :userId and u.removalFlag = false and p.removalFlag = false ")
  Page<Post> getPostsByUserId(@Param("userId") Long userId,Pageable pageable);

  Optional<Post> findById(Long id);
  Post save (Post post);
}
