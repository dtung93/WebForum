package com.example.demo.impl;

import com.example.demo.dto.GoogleDriveFileDTO;
import com.example.demo.enums.FileTag;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.GoogleDriveFileService;
import com.example.demo.utilities.Utils;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Value;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
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
public class GoogleDriveFileServiceImpl implements GoogleDriveFileService {
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

  @Autowired
  private UserPhotoRepo userPhotoRepo;

  @Autowired
  private UserRepo userRepo;

  public GoogleDriveFileServiceImpl() throws GeneralSecurityException, IOException {
    this.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    this.jsonFactory = GsonFactory.getDefaultInstance();
  }

  @Override
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

  @Override
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

  @Override
  public List<GoogleDriveFileDTO> uploadUserFile(List<MultipartFile> files, FileTag fileTag, boolean profilePhoto) throws IOException {
    String username = utils.getCurrentUsername();
    Drive driveService = getDriveService();
    List<GoogleDriveFileDTO> uploadedFiles = new ArrayList<>();
    User user = userRepo.findByUsername(username);
    if(Objects.nonNull(user)){
      for (MultipartFile file : files) {
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        ByteArrayContent mediaContent = new ByteArrayContent(file.getContentType(), file.getBytes());
        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
            .setFields("id, name, description, size, mimeType, createdTime, modifiedTime,webContentLink,webViewLink,thumbnailLink")
            .execute();

        UserPhoto image = new UserPhoto();
        image.setId(uploadedFile.getId());
        image.setUser(user);
        image.setName(uploadedFile.getName());
        image.setFileTag(fileTag);
        image.setFileSize(Math.toIntExact(uploadedFile.getSize()));
        image.setFileType(uploadedFile.getMimeType());
        image.setCreatedBy(username);
        image.setUpdatedBy(username);
        image.setCreatedDate(new Date(uploadedFile.getCreatedTime().getValue()));
        image.setUpdatedDate(new Date(uploadedFile.getModifiedTime().getValue()));
        image.setRemovalFlag(false);
        image.setDownloadLink(uploadedFile.getWebContentLink());
        image.setViewLink(uploadedFile.getThumbnailLink());
        if (profilePhoto == true) {
          image.setIsProfilePhoto(true);
        } else {
          image.setIsProfilePhoto(false);
        }
        UserPhoto savedImage = userPhotoRepo.save(image);
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
      return uploadedFiles;
    }
    return uploadedFiles;
  }


  @Override
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


  //Multimedia file. save to the video table
  private boolean isVideoFile(MultipartFile file) throws IOException {
    Tika tika = new Tika();
    String type = tika.detect(file.getInputStream());
    return type.startsWith("video/") || type.startsWith("audio/");
  }

  //Document file, textual or graphical information. save to the image table
  private boolean isImageFile(MultipartFile file) throws IOException {
    Tika tika = new Tika();
    String type = tika.detect(file.getInputStream());
    return type.startsWith("image/")
        || type.startsWith("application/vnd.ms-excel")
        || type.startsWith("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
        || type.startsWith("application/msword")
        || type.startsWith("application/pdf")
        || type.startsWith("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        || type.startsWith("application/vnd.ms-excel.sheet.macroenabled.12")
        || type.startsWith("application/vnd.ms-excel.template.macroenabled.12");
  }

  @Override
  public boolean deleteVideoFile(List<String> fileIds) throws Exception {
    try {
      List<Video> tempVideoFiles = new ArrayList<>();
      List<Video> tempSavedVideoFiles = new ArrayList<>();
      for (String id : fileIds) {
        var videoFile = videoRepo.findById(id).orElse(null);
        if (Objects.nonNull(videoFile)) {
          tempVideoFiles.add(videoFile);
          videoFile.setRemovalFlag(true);
          var savedVideo = videoRepo.save(videoFile);
          if (Objects.nonNull(savedVideo)) {
            tempSavedVideoFiles.add(savedVideo);
          }
        }
      }
      return tempVideoFiles.size() == tempSavedVideoFiles.size();

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public boolean deleteImageFile(List<String> fileIds) throws Exception {
    try {
      List<Image> tempImageFiles = new ArrayList<>();
      List<Image> tempSavedImageFiles = new ArrayList<>();
      for (String id : fileIds) {
        var imageFile = imageRepo.findById(id).orElse(null);
        if (Objects.nonNull(imageFile)) {
          tempImageFiles.add(imageFile);
          imageFile.setRemovalFlag(true);
          var savedImage = imageRepo.save(imageFile);
          if (Objects.nonNull(savedImage)) {
            tempSavedImageFiles.add(savedImage);
          }
        }
      }
      return tempImageFiles.size() == tempSavedImageFiles.size();
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
}
