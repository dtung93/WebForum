package com.example.demo.impl;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.AdminService;
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
public class AdminServiceImpl implements AdminService {
  @Autowired
  private UserRepo userRepo;

  @Autowired
  private RoleRepo roleRepo;

  @Override
  public boolean changeRoleUser(String username,Long roleId) {
    User user = userRepo.findByUsername(username);
    if(Objects.nonNull(user)){
      Role role = roleRepo.findById(roleId).orElse(null);
      user.getRoles().clear();
      user.getRoles().add(role);
      User savedUser = userRepo.save(user);
      return Objects.nonNull(savedUser);
    }
    return false;
  }
}
