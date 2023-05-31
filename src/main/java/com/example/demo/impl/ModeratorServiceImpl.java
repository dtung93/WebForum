package com.example.demo.impl;

import com.example.demo.dto.UserInfoDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public class ModeratorServiceImpl implements ModeratorService {
  @Autowired
  private UserRepo userRepo;

  @Override
  public User getByUsername(String username) {
    User user = userRepo.findByUsername(username);
    return Objects.nonNull(user) ? user : null;
  }

  @Override
  public boolean removeLogInAttempt(String username) {
    User user = this.getByUsername(username);
    if (Objects.nonNull(user)) {
      user.setLoginAttempt(0);
      User savedUser = userRepo.save(user);
      return Objects.nonNull(savedUser);
    } else
      return false;
  }
}
