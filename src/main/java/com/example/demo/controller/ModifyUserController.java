package com.example.demo.controller;

import com.example.demo.dto.EmailDTO;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.ModifyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@RequestMapping("api/modify-user")
@RestController
public class ModifyUserController {

  @Autowired
  private ModifyUserService modifyUserService;

  @PostMapping("/recover-password")
  public ResponseEntity<?> resetPassword(@RequestBody EmailDTO request) throws Exception {
    try{
      boolean isReset = modifyUserService.verifyResetPassword(request.getToken(),request.getNewPassword());
      if(Boolean.TRUE.equals(isReset)){
        return ResponseEntity.ok("Your password has been reset! Please sign in again");
      }
      else
        return ResponseEntity.internalServerError().body("Invalid token exception");
    }
    catch(Exception e){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }



}
