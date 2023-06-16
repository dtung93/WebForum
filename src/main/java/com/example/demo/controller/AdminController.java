package com.example.demo.controller;

import com.example.demo.dto.SignUpDTO;
import com.example.demo.service.AdminService;
import com.example.demo.service.ModeratorService;
import com.example.demo.utilities.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@RequestMapping("api/dxt/admin/auth")
@RestController
public class AdminController {
  @Autowired
  private ModeratorService moderatorService;

  @Autowired
  private Utils utils;

  @Autowired
  private AdminService adminService;


  @PostMapping("/change-role")
  public ResponseEntity<?> changeUserRole(HttpServletRequest http, @RequestParam String username, @RequestParam Long roleId) {
    try {
      String currentUsername = utils.getCurrentUsername();
      if (currentUsername.equals(username)) {
        return ResponseEntity.badRequest().body("Cannot modify current user.");
      }
      var result = adminService.changeRoleUser(username, roleId);
      if (Boolean.TRUE.equals(result)) {
        return ResponseEntity.ok(username + " role has successfully changed!");
      } else
        return ResponseEntity.internalServerError().body(utils.errorResult(http, "Change role failed", "Could not change user role"));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(utils.handleError(http, 2, e));
    }
  }
}
