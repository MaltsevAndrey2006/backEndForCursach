package com.dev.backendforcursach.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.google.common.io.Files.getFileExtension;

@UtilityClass
public class FileUtils {

  public static String extractFileNameFromUrl(String fullUrl) {
    var withoutProtocol = fullUrl.replaceFirst("^https?://[^/]+/", "");

    int firstSlashIndex = withoutProtocol.indexOf("/");
    if (firstSlashIndex != -1) {
      return withoutProtocol.substring(firstSlashIndex + 1);
    }

    return withoutProtocol;
  }

  public static String generateFileName(String fileName, MultipartFile file) {
    var timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    var safeAlbumName = fileName.replaceAll("[^a-zA-Z0-9]", "_");
    var extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));

    return String.format("%s_%s.%s", safeAlbumName, timestamp, extension);
  }
}
