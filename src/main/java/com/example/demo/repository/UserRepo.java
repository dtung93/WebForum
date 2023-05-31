package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Repository
public interface UserRepo extends JpaRepository<User,Long> {
  User findByUsername(String username);
  User findByEmail(String email);
  @Query(nativeQuery = true, value="SELECT * FROM USER u WHERE u.activate_token=:activate_token")
  User findByActivateToken(@Param("activate_token") String activate_token);


  @Query(nativeQuery = true, value = "SELECT * FROM USER u where u.change_password_token=:change_password_token")
  User findByChangePasswordToken(@Param("change_password_token") String change_password_token);

  @Query(nativeQuery = true, value = "SELECT  * FROM USER u WHERE u.username=:username or u.email=:username")
  List<User> findByUsernameOrEmail(@Param("username") String username);

  User save(User user);

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM USER where USERNAME=:username")
  Integer userExistUsername(@Param("username") String username);

  @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM USER where EMAIL=:email")
  Integer userExistEmail( @Param("email") String email);

}
