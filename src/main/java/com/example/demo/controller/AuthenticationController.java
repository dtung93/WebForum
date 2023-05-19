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
      UserInfoDTO user = null;
      if (Boolean.TRUE.equals(authenticationService.checkLoginAttempt(request.getUsername()))) {
        user = authenticationService.signIn(request);
      }
      else {
          var result = this.errorHandle(http, "Maximum login attempt reached", "Your account has been locked . Please contact us @123456 to unlock your account");
          return ResponseEntity.internalServerError().body(result);
      }
      if (Objects.nonNull(user)) {
        return ResponseEntity.ok(user);
      } else {
        var result = this.errorHandle(http, "Invalid Credentials", "Invalid credentials! ");
        return ResponseEntity.badRequest().body(result);
      }

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody SignUpDTO request, HttpServletRequest http) throws Exception {
    try {
      if (Objects.isNull(request.getUsername()) || StringUtils.containsWhitespace(request.getUsername()) ||
          Objects.isNull(request.getEmail()) || StringUtils.containsWhitespace(request.getEmail()) ||
          Objects.isNull(request.getPassword()) || StringUtils.containsWhitespace(request.getPassword())) {
        var result = this.errorHandle(http, "Invalid Credentials", "Username,email and password" +
            " must not be null");
        return ResponseEntity.badRequest().body(result);
      }
      boolean usernameExist = authenticationService.checkUserExistByUsername(request.getUsername());
      if (usernameExist == true) {
        var result = this.errorHandle(http, "Invalid Credentials", "This username is already taken!");
        return ResponseEntity.badRequest().body(result);
      }
      boolean emailExist = authenticationService.checkUserExistByEmail(request.getEmail());
      if (emailExist == true) {
        var result = this.errorHandle(http, "Invalid Credentials", "This email is address already taken!");
        return ResponseEntity.badRequest().body(result);
      }
      UserInfoDTO userInfoDTO = authenticationService.signUp(request);
      return ResponseEntity.ok(userInfoDTO);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  private ExceptionDTO errorHandle(HttpServletRequest http, String errorType, String description) {
    ModelMapper mapper = new ModelMapper();
    CustomException exception = new CustomException();
    exception.setStatus(HttpStatus.BAD_REQUEST.value());
    exception.setError(errorType);
    exception.setUrl(http.getRequestURI());
    exception.setDescription(description);
    exception.setTimestamp(new Date().toString());
    ExceptionDTO result = mapper.map(exception, ExceptionDTO.class);
    return result;
  }
}






