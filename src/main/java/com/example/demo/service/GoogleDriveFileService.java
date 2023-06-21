package com.example.demo.service;

import com.example.demo.dto.GoogleDriveFileDTO;
import com.example.demo.enums.FileTag;
import com.google.api.services.drive.Drive;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public interface GoogleDriveFileService {
  List<GoogleDriveFileDTO> uploadFile(List<MultipartFile> files,Long postId) throws IOException;
  List<GoogleDriveFileDTO> uploadUserFile(List<MultipartFile> files, FileTag fileTag) throws IOException;

  Drive getDriveService() throws IOException;
  boolean deleteVideoFile(List<String> fileIds) throws Exception;
  boolean deleteImageFile(List<String> fileIds) throws Exception;
  void downloadFile(String id, File file) throws IOException;

}
