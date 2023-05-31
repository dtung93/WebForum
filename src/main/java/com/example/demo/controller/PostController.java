package com.example.demo.controller;

import com.example.demo.dto.ExceptionDTO;
import com.example.demo.dto.NewThreadDTO;
import com.example.demo.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@RequestMapping("api/post/")
public class PostController {
  @Autowired
  private ThreadService threadService;

  @PostMapping("new-thread")
  public ResponseEntity<?> createThread(HttpServletRequest http, NewThreadDTO request) {
    try {
      Map<String, Object> map = threadService.createThread(request);
      if (map.containsKey("error")) {
        var result = this.handleError(http, map);
        return ResponseEntity.internalServerError().body(result);
      } else
        return ResponseEntity.ok(map);
    } catch (Exception e) {
      ExceptionDTO exceptionDTO = new ExceptionDTO();
      exceptionDTO.setError("Exception");
      exceptionDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      exceptionDTO.setUrl(http.getRequestURI());
      exceptionDTO.setDescription(e.getMessage());
      exceptionDTO.setTimestamp(new Date().toString());
      return ResponseEntity.internalServerError().body(exceptionDTO);
    }
  }

  private ExceptionDTO handleError(HttpServletRequest http, Map<String, Object> map) {
    ExceptionDTO exceptionDTO = new ExceptionDTO();
    exceptionDTO.setError("Exception");
    exceptionDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    exceptionDTO.setUrl(http.getRequestURI());
    exceptionDTO.setDescription(map.get("error").toString());
    exceptionDTO.setTimestamp(new Date().toString());
    return exceptionDTO;
  }
}
