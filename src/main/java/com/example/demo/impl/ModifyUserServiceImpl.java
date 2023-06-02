package com.example.demo.impl;

import com.example.demo.dto.UserInfoDTO;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.ModifyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class ModifyUserServiceImpl implements ModifyUserService {
  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private UserRepo userRepo;

  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  public UserInfoDTO updateUser(User user) {
    return null;
  }

  @Override
  public boolean verifyConfirmAccount(String token) throws Exception {
    try {
      User user = userRepo.findByActivateToken(token);
      if (Objects.nonNull(user)) {
        boolean isTokenExpired = jwtTokenProvider.checkIsTokenExpired(user.getActivateToken());
        if (Boolean.FALSE.equals(isTokenExpired)) {
          user.setActivateToken(null);
          userRepo.save(user);
          return true;
        }
        else
          return false;
      } else
        return false;
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }


  @Override
  public boolean verifyResetPassword(String token, String newPassword) throws Exception {
    try {
      User user = userRepo.findByChangePasswordToken(token);
      if (Objects.nonNull(user)) {
        boolean isTokenExpired = jwtTokenProvider.checkIsTokenExpired(user.getChangePasswordToken());
        if(Boolean.FALSE.equals(isTokenExpired)){
          user.setChangePasswordToken(null);
          String encodedPassword = passwordEncoder.encode(newPassword);
          user.setPassword(encodedPassword);
          userRepo.save(user);
          return true;
        }
        else
          return false;
      } else
        return false;
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

  }
}
