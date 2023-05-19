package com.example.demo.service;

import com.example.demo.dto.AuthenticateDTO;
import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.dto.UserStatisticsDTO;
import org.springframework.stereotype.Service;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public interface AuthenticationService {
  UserInfoDTO signIn(AuthenticateDTO request) throws Exception;
  UserStatisticsDTO getUserStatistics(String username);

  UserInfoDTO signUp(SignUpDTO request);

  boolean checkUserExistByUsername(String username);
  boolean checkUserExistByEmail(String email);

  boolean checkLoginAttempt(String username);

  String authenticate(String username, String password) throws Exception;

}
