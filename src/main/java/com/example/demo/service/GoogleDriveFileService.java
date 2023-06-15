package com.example.demo.service;

import com.example.demo.dto.GoogleDriveFileDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
  List<GoogleDriveFileDTO> getAllFile() throws IOException, GeneralSecurityException;
  void deleteFile(String id) throws Exception;
  void uploadFile(MultipartFile file, String filePath, boolean isPublic);
  void downloadFile(String id, OutputStream outputStream) throws IOException, GeneralSecurityException;
}
