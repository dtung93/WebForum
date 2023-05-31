package com.example.demo.controller;

import com.example.demo.dto.AuthenticateDTO;
import com.example.demo.dto.SignUpDTO;
import com.example.demo.model.CustomException;
import com.example.demo.dto.ExceptionDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
@RequestMapping("api/auth")
public class AuthenticationController {
  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/sign-in")
  public ResponseEntity<?> signIn(@RequestBody AuthenticateDTO request, HttpServletRequest http) throws Exception {
    try {
      Map<String, Object> response = new HashMap<>();
      response = authenticationService.signIn(request);
      if (response.containsKey("authenticationError")) {
        ExceptionDTO error = this.errorHandle(http, response);
        return ResponseEntity.badRequest().body(error);

      } else {
        return ResponseEntity.ok(response);
      }

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody SignUpDTO request, HttpServletRequest http) throws Exception {
    try {
      Map<String, Object> response = new HashMap<>();
      response = authenticationService.signUp(request);
      if (response.containsKey("invalidCredentials")) {
        ExceptionDTO error = this.errorHandle(http, response);
        return ResponseEntity.badRequest().body(error);
      } else
        return ResponseEntity.ok(response);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  private ExceptionDTO errorHandle(HttpServletRequest http, Map<String, Object> map) {
    String keyError = Objects.nonNull(map.get("authenticationError")) ? "authenticationError" : "invalidCredentials";
    ModelMapper mapper = new ModelMapper();
    CustomException exception = new CustomException();
    exception.setStatus(HttpStatus.BAD_REQUEST.value());
    exception.setError(keyError);
    exception.setUrl(http.getRequestURI());
    exception.setDescription(map.get(keyError).toString());
    exception.setTimestamp(new Date().toString());
    ExceptionDTO result = mapper.map(exception, ExceptionDTO.class);
    return result;
  }
}






