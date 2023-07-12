package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
  @Query(nativeQuery = true, value = "SELECT u.username AS user_name, r.name AS role_name FROM user u " +
      "INNER JOIN user_role ur ON u.id = ur.user_id " +
      "INNER JOIN role r ON ur.role_id = r.id " +
      "WHERE u.username IN :usernames"
  )
  List<Map<String,Object>> getUserRoles(@Param("usernames")List<String> usernames);

  @Query(nativeQuery = true, value = "SELECT * FROM user u WHERE U.username = :username and u.removal_flag = 0")
  User findByUsername(String username);

  @Query(nativeQuery = true,value = "SELECT * FROM user u WHERE U.username = :email and u.removal_flag = 0")
  User findByEmail(String email);

  @Query(nativeQuery = true, value = "SELECT * FROM USER u WHERE u.activate_token=:activate_token")
  User findByActivateToken(@Param("activate_token") String activate_token);

  @Query(nativeQuery = true, value = "SELECT * FROM  USER u where ( :username IS NULL OR u.username like CONCAT('%',:username,'%')) " +
      "OR u.email like CONCAT('%',:username,'%')")
  Page<User> getAllUsers(@Param("username") String username,
                         Pageable pageable);

  @Query(nativeQuery = true, value = "SELECT * FROM USER u WHERE (:username IS NULL OR u.username LIKE CONCAT('%', :username, '%')) " +
      "AND (u.email LIKE CONCAT('%', :username, '%') AND (u.created_date BETWEEN :startDate AND :endDate))")
  Page<User> getUsersByUsernameAndCreatedDate(@Param("username") String username,
                                              @Param("startDate") Date startDate,
                                              @Param("endDate") Date endDate,
                                              Pageable pageable);

  @Query(nativeQuery = true, value = "SELECT * FROM  USER u where ( :username IS NULL OR u.username like CONCAT('%',:username,'%')) " +
      "OR u.email like CONCAT('%',:username,'%') AND (u.updated_date BETWEEN :startDate AND :endDate)")
  Page<User> getUsersByUsernameAndModifyDate(@Param("username") String username,
                                              @Param("startDate") Date startDate,
                                              @Param("endDate") Date endDate,
                                              Pageable pageable);

  @Query(nativeQuery = true, value = "SELECT * FROM USER u where u.change_password_token=:change_password_token")
  User findByChangePasswordToken(@Param("change_password_token") String change_password_token);

  @Query(nativeQuery = true, value = "SELECT  * FROM USER u WHERE u.username=:username or u.email=:username and u.removal_flag = 0")
  List<User> findByUsernameOrEmail(@Param("username") String username);

  User save(User user);

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM USER where USERNAME=:username")
  Integer userExistUsername(@Param("username") String username);

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM USER where EMAIL=:email")
  Integer userExistEmail(@Param("email") String email);

}
