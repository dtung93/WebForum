package com.example.demo.controller;

import com.example.demo.dto.EmailDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.enums.FileTag;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.GoogleDriveFileService;
import com.example.demo.service.ModifyUserService;
import com.example.demo.utilities.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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

  @Autowired
  private Utils utils;

  @Autowired
  private GoogleDriveFileService googleDriveFileService;

  @PostMapping("/recover-password")
  public ResponseEntity<?> resetPassword(@RequestBody EmailDTO request) throws Exception {
    try {
      boolean isReset = modifyUserService.verifyResetPassword(request.getToken(), request.getNewPassword());
      if (Boolean.TRUE.equals(isReset)) {
        return ResponseEntity.ok("Your password has been reset! Please sign in again");
      } else
        return ResponseEntity.internalServerError().body("Invalid token exception");
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @PreAuthorize("#username == authentication.name")
  @PostMapping("/upload-file/{username}")
  public ResponseEntity<?> uploadUserFile(HttpServletRequest http,
                                          @PathVariable("username") String username,
                                          @RequestParam("files") List<MultipartFile> files,
                                          @RequestParam("fileTag") FileTag fileTag,
                                          @RequestParam("isProfilePhoto") boolean isProfilePhoto
  ) {
    try {
      var result = googleDriveFileService.uploadUserFile(files, fileTag, isProfilePhoto);
      if (!result.isEmpty()) {
        return ResponseEntity.ok(new ResponseDTO(200, http.getRequestURI(), result, new Date().toString()));
      } else
        return ResponseEntity.internalServerError().body(utils.errorResult(http, "500", "Could not upload the files!"));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(utils.handleError(http, 2, e));
    }
  }


}
