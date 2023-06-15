package com.example.demo.controller;

import com.example.demo.impl.GoogleDriveFileServiceImpl;
import com.example.demo.utilities.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@RestController
@RequestMapping("api/file")
public class FileController {

  @Autowired
  private GoogleDriveFileServiceImpl googleDriveFileService;

  @Autowired
  private Utils utils;

  @PostMapping("/upload-file/{username}")
  @PreAuthorize("#username == authentication.name")
  public ResponseEntity<?> uploadFile(HttpServletRequest http,
                                      @PathVariable("username") String username,
                                      @RequestParam("files") List<MultipartFile> files,
                                      @RequestParam("postId") Long postId
  ) {
    try {
      var result = googleDriveFileService.uploadFile(files, postId);
      if (result.isEmpty()) {
        return ResponseEntity.internalServerError().body(utils.errorResult(http, "Upload operation failed", "Files could not be uploaded to the server"));
      } else
        return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(utils.handleError(http, 2, e));
    }
  }

  @GetMapping("/download/{fileId}")
  public ResponseEntity<?> downloadFile(HttpServletRequest http, @PathVariable("fileId") String fileId) {
    try {
      File downloadedFile = File.createTempFile("temp", ".tmp");
      googleDriveFileService.downloadFile(fileId, downloadedFile);
      // Create a Resource object from the downloaded file
      FileSystemResource resource = new FileSystemResource(downloadedFile);

      // Set the content disposition header to force download
      HttpHeaders headers = new HttpHeaders();
      headers.setContentDisposition(ContentDisposition.attachment()
          .filename(resource.getFilename())
          .build());

      // Return the file as a ResponseEntity with headers and status OK
      return ResponseEntity.ok()
          .headers(headers)
          .contentLength(resource.contentLength())
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(resource);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(utils.handleError(http, 2, e));
    }
  }

}
