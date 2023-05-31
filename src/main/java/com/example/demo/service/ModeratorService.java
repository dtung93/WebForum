package com.example.demo.service;

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
public interface ModeratorService {

  User getByUsername(String username);

  boolean removeLogInAttempt(String username);
}
