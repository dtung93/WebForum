package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */

@RequestMapping("api/dxt/mod/auth")
public class ModeratorController {

  @Autowired
  private ModeratorService moderatorService;

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

}
