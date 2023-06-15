package com.example.demo.controller;

import com.example.demo.dto.EmailDTO;
import com.example.demo.dto.ExceptionDTO;
import com.example.demo.model.CustomException;
import com.example.demo.model.User;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.MailService;
import com.example.demo.service.ModifyUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
@RequestMapping("api/mail")
public class MailController {
  @Autowired
  private MailService mailService;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private ModifyUserService modifyUserService;

  @PostMapping("/recover-password")
  public ResponseEntity<?> changePasswordEmail(@RequestBody EmailDTO request) throws Exception {
    try {
      boolean sent = mailService.sendChangePasswordEmail(request);
      if (Boolean.TRUE.equals(sent)) {
        return ResponseEntity.ok("Password recovery instructions have been sent to your email address " + request.getEmail());
      } else
        return ResponseEntity.badRequest().body("Could not found email associated with " + request.getEmail());
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.valueOf(e.getMessage()));
    }
  }

  @GetMapping("/verify")
  public ResponseEntity<?> confirmAccount(@RequestParam("activationToken") String activationToken) throws Exception {
    try {
      boolean verified = modifyUserService.verifyConfirmAccount(activationToken);
      if (Boolean.TRUE.equals(verified)) {
        return ResponseEntity.ok("Thank you! Your account has been verified");
      } else
        return ResponseEntity.internalServerError().body("Invalid or expired token!");
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

}
