package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@RestController
public class HomeController {
  private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

  @GetMapping("/home")
  public ResponseEntity<?> test() {
    logger.info("START PROCESS");
    return ResponseEntity.ok("hello boy");
  }

}
