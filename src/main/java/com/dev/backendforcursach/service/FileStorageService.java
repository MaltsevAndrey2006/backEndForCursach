package com.dev.backendforcursach.service;

import com.dev.backendforcursach.enums.FileType;
import com.dev.backendforcursach.utils.FileUtils;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {
  private final MinioClient minioClient;

  @Value("${minio.endpoint}")
  private String endpoint;

  @Value("${minio.bucket.images}")
  private String imageBucket;

  @Value("${minio.bucket.songs}")
  private String songsBucket;

  public String uploadFile(MultipartFile file, String basicFileName, FileType type) {
    var fileName = FileUtils.generateFileName(basicFileName, file);
    try {
      var bucket = getBucket(type);
      minioClient.putObject(PutObjectArgs.builder()
          .bucket(bucket)
          .object(fileName)
          .stream(file.getInputStream(), file.getSize(), -1)
          .contentType(file.getContentType())
          .build());
      return String.format("%s/%s/%s", endpoint, bucket, fileName);
    } catch (Exception e) {
      log.error("Error to upload file error: {}", e.getMessage());
      throw new RuntimeException("Error to upload file");
    }
  }

  public void deleteFile(String url, FileType fileType) {
    try {
      var bucket = getBucket(fileType);
      minioClient.removeObject(
          RemoveObjectArgs.builder()
              .bucket(bucket)
              .object(FileUtils.extractFileNameFromUrl(url))
              .build());
      log.info("File deleted: {}, {}", bucket, FileUtils.extractFileNameFromUrl(url));
    } catch (Exception e) {
      throw new RuntimeException("Failed to delete file");
    }
  }

  private String getBucket(FileType type) {
    return type.equals(FileType.SONG) ? songsBucket : imageBucket;
  }
}
