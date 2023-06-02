package com.example.demo.controller;

import com.example.demo.dto.ExceptionDTO;
import com.example.demo.dto.NewThreadDTO;
import com.example.demo.dto.PostDTO;
import com.example.demo.service.PostService;
import com.example.demo.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@RequestMapping("api/post/")
@RestController
public class PostController {
  @Autowired
  private ThreadService threadService;

  @Autowired
  private PostService postService;

  @PostMapping("new-thread")
  public ResponseEntity<?> createThread(HttpServletRequest http, @RequestBody NewThreadDTO request) {
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

  @PreAuthorize("#username == authentication.name")
  @PostMapping("/new-post/{username}/{threadId}")
  public ResponseEntity<?> comment (@PathVariable String username, @PathVariable Long threadId, @RequestBody PostDTO postDTO, HttpServletRequest http ){
    try{
      String invalid = "invalid";
      Map<String,Object> result = postService.comment(username,threadId,postDTO.getContent());
      for(String key : result.keySet()){
        if(key.contains(invalid.toLowerCase())){
          return ResponseEntity.internalServerError().body(result);
        }
        else{
          return ResponseEntity.ok(result);
        }
      }
      return ResponseEntity.internalServerError().body(null);
    }
    catch (Exception e){
      ExceptionDTO exceptionDTO = new ExceptionDTO();
      exceptionDTO.setError("Exception");
      exceptionDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      exceptionDTO.setUrl(http.getRequestURI());
      exceptionDTO.setDescription(e.getMessage());
      exceptionDTO.setTimestamp(new Date().toString());
      return ResponseEntity.internalServerError().body(exceptionDTO);
    }
  }

  @GetMapping("/thread")
  public ResponseEntity<?> goToThread(HttpServletRequest http, @RequestParam("threadId") Long threadId) {
    try {
      List<PostDTO> result = postService.getPostByThread(threadId);
      if (Objects.nonNull(result) && !result.isEmpty()) {
        return ResponseEntity.ok(result);
      } else
        return ResponseEntity.internalServerError().body("Invalid thread not found!");
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
