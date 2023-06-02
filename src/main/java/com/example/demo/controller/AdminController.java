package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping("/{username}/test")
  @PreAuthorize("#username == authentication.name")
  public ResponseEntity<?> getTest(){
    return ResponseEntity.ok("TEST ADMIN");
  }
}
