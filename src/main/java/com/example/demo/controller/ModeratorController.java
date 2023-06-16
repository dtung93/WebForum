package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.ModeratorService;
import com.example.demo.utilities.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */

@RequestMapping("api/dxt/mod/auth")
@RestController
public class ModeratorController {

  @Autowired
  private ModeratorService moderatorService;

  @Autowired
  private Utils utils;

  @PostMapping("/reset-sign-in-attempt")
  public ResponseEntity<?> removeLoginAttempt(@RequestParam("username") String username) {
    try {
      boolean result = moderatorService.removeLogInAttempt(username);
      if (Boolean.TRUE.equals(result)) {
        return ResponseEntity.ok("Username " + username + " log-in attempt has successfully reset!");
      } else
        return ResponseEntity.badRequest().body("Username " + username + " not found");
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }

  }

  @PostMapping("/get-users")
  public ResponseEntity<?> getUsers(HttpServletRequest http,
                                    @RequestParam String username,
                                    @RequestParam Date startDate,
                                    @RequestParam Date endDate,
                                    @RequestParam(defaultValue = "0") int pageNumber,
                                    @RequestParam(defaultValue = "30") int pageSize) {

    try {
      var result = moderatorService.getAllUsers(username, pageNumber, pageSize);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(utils.handleError(http, 2, e));
    }
  }

  @PostMapping("/get-users-by-created-date")
  public ResponseEntity<?> getUsersByCreatedDate(HttpServletRequest http,
                                                 @RequestParam String username,
                                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                 @RequestParam(defaultValue = "0") int pageNumber,
                                                 @RequestParam(defaultValue = "30") int pageSize) {

    try {
      // 86399999= 23h:59m:59s in miliseconds
      Date finalEndDate = new Date(endDate.getTime() + 86399999);
      var result = moderatorService.getAllUsersByCreatedDate(username, startDate, finalEndDate, pageNumber, pageSize);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(utils.handleError(http, 2, e));
    }
  }

  @PostMapping("/get-users-by-modified-date")
  public ResponseEntity<?> getUsersByModifiedDate(HttpServletRequest http,
                                                 @RequestParam String username,
                                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                 @RequestParam(defaultValue = "0") int pageNumber,
                                                 @RequestParam(defaultValue = "30") int pageSize) {

    try {
      // 86399999= 23h:59m:59s in miliseconds
      Date finalEndDate = new Date(endDate.getTime() + 86399999);
      var result = moderatorService.getAllUsersByModifiedDate(username, startDate, finalEndDate, pageNumber, pageSize);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(utils.handleError(http, 2, e));
    }
  }

}
