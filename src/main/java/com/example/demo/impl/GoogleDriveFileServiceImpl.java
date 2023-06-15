package com.example.demo.impl;

import com.example.demo.dto.GoogleDriveFileDTO;
import com.example.demo.enums.FileTag;
import com.example.demo.enums.FileTag;
import com.example.demo.model.Image;
import com.example.demo.model.Post;
import com.example.demo.model.Video;
import com.example.demo.repository.ImageRepo;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.VideoRepo;
import com.example.demo.utilities.Utils;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Value;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionList;
import com.google.api.services.drive.model.User;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;


/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public class GoogleDriveFileServiceImpl {
  @Value("${spring.security.oauth2.client.app-name}")
  private String APPLICATION_NAME;

  private static final String clientSecretsFilePath = "static/clientsecrets.json";

  private final HttpTransport httpTransport;
  private final JsonFactory jsonFactory;

  @Autowired
  private Utils utils;

  @Autowired
  private PostRepo postRepo;

  @Autowired
  private ImageRepo imageRepo;

  @Autowired
  private VideoRepo videoRepo;

  public GoogleDriveFileServiceImpl() throws GeneralSecurityException, IOException {
    this.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    this.jsonFactory = GsonFactory.getDefaultInstance();
  }

  public Drive getDriveService() throws IOException {
    Credential credential = authorize();
    return new Drive.Builder(httpTransport, jsonFactory, credential)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }

  private Credential authorize() throws IOException {
    List<String> scopes = Collections.singletonList(DriveScopes.DRIVE);
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(new ClassPathResource(clientSecretsFilePath).getInputStream()));

    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        httpTransport, jsonFactory, clientSecrets, scopes)
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(System.getProperty("user.home"), "Documents/dxt-forum")))
        .setAccessType("offline")
        .build();

    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver)
        .authorize("user");

    return credential;
  }

  public List<GoogleDriveFileDTO> uploadFile(List<MultipartFile> files, Long postId) throws IOException {
    try {
      String username = utils.getCurrentUsername();
      Drive driveService = getDriveService();
      List<GoogleDriveFileDTO> uploadedFiles = new ArrayList<>();
      Post post = postRepo.findById(postId).orElse(null);
      if (Objects.nonNull(post)) {
        List<MultipartFile> videoFiles = new ArrayList<>();
        List<MultipartFile> imageFiles = new ArrayList<>();
        for (MultipartFile file : files) {
          if (Boolean.TRUE.equals(this.isImageFile(file))) {
            imageFiles.add(file);
          } else if (Boolean.TRUE.equals(this.isVideoFile(file))) {
            videoFiles.add(file);
          }
        }
        if (!imageFiles.isEmpty()) {
          for (MultipartFile file : imageFiles) {
            File fileMetadata = new File();
            fileMetadata.setName(file.getOriginalFilename());
            ByteArrayContent mediaContent = new ByteArrayContent(file.getContentType(), file.getBytes());
            File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id, name, description, size, mimeType, createdTime, modifiedTime,webContentLink,webViewLink,thumbnailLink")
                .execute();

            Image image = new Image();
            image.setId(uploadedFile.getId());
            image.setFileName(uploadedFile.getName());
            image.setFileSize(Math.toIntExact(uploadedFile.getSize()));
            image.setFileType(uploadedFile.getMimeType());
            image.setDescription(uploadedFile.getDescription());
            image.setPost(post);
            image.setCreatedBy(username);
            image.setUpdatedBy(username);
            image.setCreatedDate(new Date(uploadedFile.getCreatedTime().getValue()));
            image.setUpdatedDate(new Date(uploadedFile.getModifiedTime().getValue()));
            image.setRemovalFlag(false);
            image.setWebContentLink(uploadedFile.getWebContentLink());
            image.setWebViewLink(uploadedFile.getWebViewLink());
            image.setThumbNailLink(uploadedFile.getThumbnailLink());
            Image savedImage = imageRepo.save(image);
            if (Objects.nonNull(savedImage)) {
              GoogleDriveFileDTO googleDriveFileDTO = new GoogleDriveFileDTO();
              googleDriveFileDTO.setId(uploadedFile.getId());
              googleDriveFileDTO.setName(uploadedFile.getName());
              googleDriveFileDTO.setShared(true);
              googleDriveFileDTO.setCreatedBy(utils.getCurrentUsername());
              googleDriveFileDTO.setUpdatedBy(utils.getCurrentUsername());
              googleDriveFileDTO.setCreatedDate(new Date());
              googleDriveFileDTO.setUpdatedDate(googleDriveFileDTO.getCreatedDate());
              googleDriveFileDTO.setSize(utils.getFileSize(uploadedFile.getSize()));
              googleDriveFileDTO.setWebViewLink(uploadedFile.getWebViewLink());
              googleDriveFileDTO.setWebViewContentLink(uploadedFile.getWebContentLink());
              googleDriveFileDTO.setThumbNailLink(uploadedFile.getThumbnailLink());
              uploadedFiles.add(googleDriveFileDTO);
            }
          }
        }
        if (!videoFiles.isEmpty()) {
          for (MultipartFile file : videoFiles) {
            File fileMetadata = new File();
            fileMetadata.setName(file.getOriginalFilename());
            ByteArrayContent mediaContent = new ByteArrayContent(file.getContentType(), file.getBytes());
            File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id, name, description, size, mimeType, createdTime, modifiedTime,webContentLink,webViewLink,thumbnailLink")
                .execute();

            Video video = new Video();
            video.setId(uploadedFile.getId());
            video.setTitle(uploadedFile.getName());
            video.setFileSize(Math.toIntExact(uploadedFile.getSize()));
            video.setFileType(uploadedFile.getFileExtension());
            video.setDescription(uploadedFile.getDescription());
            video.setPost(post);
            video.setCreatedBy(username);
            video.setCreatedDate(new Date(uploadedFile.getCreatedTime().getValue()));
            video.setUpdatedBy(username);
            video.setUpdatedDate(new Date(uploadedFile.getModifiedTime().getValue()));
            video.setRemovalFlag(false);
            video.setWebContentLink(uploadedFile.getWebContentLink());
            video.setThumbNailLink(uploadedFile.getThumbnailLink());
            video.setWebViewLink(uploadedFile.getWebViewLink());
            Video savedVideo = videoRepo.save(video);
            if (Objects.nonNull(savedVideo)) {
              GoogleDriveFileDTO googleDriveFileDTO = new GoogleDriveFileDTO();
              googleDriveFileDTO.setId(uploadedFile.getId());
              googleDriveFileDTO.setName(uploadedFile.getName());
              googleDriveFileDTO.setShared(true);
              googleDriveFileDTO.setCreatedBy(utils.getCurrentUsername());
              googleDriveFileDTO.setUpdatedBy(utils.getCurrentUsername());
              googleDriveFileDTO.setCreatedDate(new Date());
              googleDriveFileDTO.setUpdatedDate(googleDriveFileDTO.getCreatedDate());
              googleDriveFileDTO.setSize(utils.getFileSize(uploadedFile.getSize()));
              googleDriveFileDTO.setWebViewLink(uploadedFile.getWebViewLink());
              googleDriveFileDTO.setWebViewContentLink(uploadedFile.getWebContentLink());
              googleDriveFileDTO.setThumbNailLink(uploadedFile.getThumbnailLink());
              uploadedFiles.add(googleDriveFileDTO);
            }

          }
        }
      }

      return uploadedFiles;
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }


  public void downloadFile(String fileId, java.io.File destinationFile) throws IOException {
    Drive driveService = getDriveService();

    try (OutputStream outputStream = new FileOutputStream(destinationFile)) {
      driveService.files().get(fileId)
          .executeMediaAndDownloadTo(outputStream);
    }
  }

  public String getViewLink(String fileId) throws IOException {
    Drive driveService = getDriveService();

    File file = driveService.files().get(fileId)
        .setFields("webViewLink")
        .execute();

    return file.getWebViewLink();
  }

  private boolean isVideoFile(MultipartFile file) throws IOException {
    Tika tika = new Tika();
    String type = tika.detect(file.getInputStream());
    return type.startsWith("video/") || type.startsWith("audio/");
  }

  private boolean isImageFile(MultipartFile file) throws IOException {
    Tika tika = new Tika();
    String type = tika.detect(file.getInputStream());
    return type.startsWith("image/")
        ||type.startsWith("application/vnd.ms-excel")
        ||type.startsWith("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
        ||type.startsWith("application/msword")
        ||type.startsWith("application/pdf");
  }
}
