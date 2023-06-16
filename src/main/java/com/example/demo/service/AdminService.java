package com.example.demo.service;

import org.springframework.stereotype.Service;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public interface AdminService {
  boolean changeRoleUser(String username, Long roleId);
}
