package com.example.demo.impl;

import com.example.demo.dto.UserInfoDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.ModeratorService;
import com.example.demo.utilities.Utils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

  @Autowired
  private Utils utils;

  @Override
  public User getByUsername(String username) {
    User user = userRepo.findByUsername(username);
    return Objects.nonNull(user)? user : null;
  }

  @Override
  public Map<String, Object> getAllUsers(String username, int pageNumber, int pageSize) {
    String key = "all_users";
    int offset = pageNumber * pageSize;
    Pageable pageable = new PageDataOffset(offset, pageSize, Sort.by("created_date").descending());
    Page<User> pages = userRepo.getAllUsers(username, pageable);
    return utils.getPage(pages);
  }

  @Override
  public Map<String, Object> getAllUsersByCreatedDate(String username, Date startDate, Date endDate, int pageNumber, int pageSize) {
    int offset = pageNumber * pageSize;
    Pageable pageable = new PageDataOffset(offset, pageSize, Sort.by("created_date").descending());
    Page<User> pages = userRepo.getUsersByUsernameAndCreatedDate(username, startDate, endDate, pageable);
    return utils.getPage(pages);
  }

  @Override
  public Map<String, Object> getAllUsersByModifiedDate(String username, Date startDate, Date endDate, int pageNumber, int pageSize) {
    int offset = pageNumber * pageSize;
    Pageable pageable = new PageDataOffset(offset, pageSize, Sort.by("created_date").descending());
    Page<User> pages = userRepo.getUsersByUsernameAndModifyDate(username, startDate, endDate, pageable);
    return utils.getPage(pages);
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
