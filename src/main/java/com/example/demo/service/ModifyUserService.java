package com.example.demo.service;

import com.example.demo.dto.UserInfoDTO;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public interface ModifyUserService {
  UserInfoDTO updateUser(User user);

  boolean verifyConfirmAccount(String activateToken) throws Exception;

  boolean verifyResetPassword(String token, String newPassword) throws Exception;
}
