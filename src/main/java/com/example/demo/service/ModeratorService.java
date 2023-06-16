package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

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
  Map<String,Object> getAllUsers(String username, int pageNumber, int pageSize);
  Map<String,Object> getAllUsersByCreatedDate(String username,Date startDate, Date endDate, int pageNumber, int pageSize);
  Map<String,Object> getAllUsersByModifiedDate(String username,Date startDate, Date endDate, int pageNumber, int pageSize);

  boolean removeLogInAttempt(String username);
}
