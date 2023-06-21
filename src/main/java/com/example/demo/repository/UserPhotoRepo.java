package com.example.demo.repository;

import com.example.demo.model.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
public interface UserPhotoRepo extends JpaRepository<UserPhoto,String> {

  @Query(nativeQuery = true,value = "SELECT * FROM USER_PHOTO u where u.id=:id")
  List<UserPhoto>  getAllById (String id);

  UserPhoto save(UserPhoto userPhoto);
}
