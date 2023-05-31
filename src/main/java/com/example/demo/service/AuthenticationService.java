package com.example.demo.service;

import com.example.demo.dto.AuthenticateDTO;
import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.dto.UserStatisticsDTO;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public interface AuthenticationService {
  Map<String,Object> signIn(AuthenticateDTO request) throws Exception;
  UserStatisticsDTO getUserStatistics(String username);

  Map<String,Object> signUp(SignUpDTO request) throws Exception;

  boolean checkUserExistByUsername(String username);
  boolean checkUserExistByEmail(String email);

  String authenticate(String username, String password) throws Exception;

  boolean verifyConfirmAccount(String activateToken) throws Exception;

  boolean verifyResetPassword(String token, String newPassword) throws Exception;
}
