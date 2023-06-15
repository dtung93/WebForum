package com.example.demo.repository;

import com.example.demo.model.Video;
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
public interface VideoRepo extends JpaRepository<Video,String> {
  Video save(Video video);
  Optional<Video> findById(String id);
}
