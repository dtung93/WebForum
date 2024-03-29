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
public interface ThreadRepo extends JpaRepository<Thread, Long> {
  @Query("SELECT t FROM Thread t WHERE t.id = :id and  t.removalFlag = false")
  Page<Thread> getThreadById(@Param("id") Long id, Pageable pageable);

  @Query("SELECT t FROM Thread t WHERE t.category = :category and t.removalFlag = false")
  Page<Thread> getThreadByCategory(@Param("category") ThreadCategory category, Pageable pageable);

  @Query(nativeQuery = true, value = "" +
      "select t.id as id, t.category as category, t.title as thread_title, t.views as views," +
      " t.last_replied as last_replied_by, u.username as created_by, " +
      "t.updated_by as updated_by, t.updated_date as updated_date, t.created_date as created_date, " +
      "(SELECT COUNT(*) FROM post p where p.thread_id=t.id) as post_count" +
      " from thread t join user u on t.user_id= u.id " +
      "where t.category IS NOT NULL AND t.removal_flag=false")
  List<Map<String, Object>> getAllThread();

  Optional<Thread> findById(Long id);

  Thread save(Thread thread);
}
