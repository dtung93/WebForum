package com.example.demo.controller;

import com.example.demo.dto.NewThreadDTO;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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

  @PostMapping("/test")
  public ResponseEntity<?> test2(@RequestBody NewThreadDTO request){
    if(Objects.nonNull(request)){
      return ResponseEntity.ok("TEST SUCCESS");
    }
    else
      return ResponseEntity.internalServerError().body("FAILED TEST");

  }

}
