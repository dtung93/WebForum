package com.example.demo.controller;

import com.example.demo.dto.NewThreadDTO;
import com.example.demo.dto.ThreadDTO;
import com.example.demo.enums.ThreadCategory;
import com.example.demo.impl.PageDataOffset;
import com.example.demo.service.ThreadService;
import com.example.demo.utilities.Utils;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@RestController
@RequestMapping("home")
public class HomeController {
  @Autowired
  private ThreadService threadService;

  @Autowired
  private Utils utils;

  private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


  @PostMapping("/{username}/test")
  @PreAuthorize("#username == authentication.name")
  public ResponseEntity<?> test2(@PathVariable String username, @RequestBody NewThreadDTO request) {
    if (Objects.nonNull(request)) {
      return ResponseEntity.ok("TEST SUCCESS");
    } else
      return ResponseEntity.internalServerError().body("FAILED TEST");
  }

  @GetMapping("/{threadCategory}")
  public ResponseEntity<?> getThreadByCat(HttpServletRequest http, @PathVariable ThreadCategory threadCategory,
                                          @RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "30") int pageSize
  ) {
    try {
      Map<String, Object> output = new HashMap<>();
      output = threadService.getThreadByCategory(threadCategory, pageNumber, pageSize);
      return ResponseEntity.ok(output);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(utils.handleError(http, 2, e));
    }
  }
}
