package com.example.demo.repository;

import com.example.demo.enums.ThreadCategory;
import com.example.demo.model.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
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
public interface ThreadRepo extends JpaRepository<Thread,Long> {
  @Query("SELECT t FROM Thread t WHERE t.id = :id")
  Page<Thread> getThreadById(@Param("id") Long id, Pageable pageable);

  @Query("SELECT t FROM Thread t WHERE t.category=:category")
  Page<Thread> getThreadByCategory(@Param("category") ThreadCategory category, Pageable pageable);

  Optional<Thread> findById(Long id);

  Thread save(Thread thread);
}
