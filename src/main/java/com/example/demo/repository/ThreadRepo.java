package com.example.demo.repository;

import com.example.demo.model.Thread;
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
public interface ThreadRepo extends JpaRepository<Thread,Long> {
  Optional<Thread> findById(Long id);

  Thread save(Thread thread);
}
