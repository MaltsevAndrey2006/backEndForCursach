package andrey.dev.backendforcursach.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.google.common.io.Files.getFileExtension;

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

    @SneakyThrows
    public String uploadImage(MultipartFile imageFile, String albumName) {
        if (!isImageFile(imageFile)) {
            throw new RuntimeException("File must be an image (JPEG, PNG, etc.)");
        }
        String fileName = generateImageFileName(albumName, imageFile);
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(imageBucket)
                .object(fileName)
                .stream(imageFile.getInputStream(), imageFile.getSize(), -1)
                .contentType(imageFile.getContentType())
                .build());
        return String.format("%s/%s/%s", endpoint, imageBucket, fileName);
    }

    @SneakyThrows
    public String uploadMusic(MultipartFile musicFile, String songName) {
        if (!isAudioFile(musicFile)) {
            throw new RuntimeException("File must be an audio file");
        }

        String fileName = generateAudioFileName(songName, musicFile);
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(songsBucket)
                .object(fileName)
                .stream(musicFile.getInputStream(), musicFile.getSize(), -1)
                .contentType(musicFile.getContentType())
                .build());
        return String.format("%s/%s/%s", endpoint, songsBucket, fileName);
    }

    public void deleteMusicFile(String url) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(songsBucket)
                            .object(extractFileNameFromUrl(url))
                            .build()
            );
            log.info("File deleted: {}/{}", songsBucket, extractFileNameFromUrl(url));
        } catch (Exception e) {
            log.error("Failed to delete file", e);
        }
    }

    public void deleteImageFile(String url) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(imageBucket)
                            .object(extractFileNameFromUrl(url))
                            .build()
            );
            log.info("File deleted: {}/{}", imageBucket, extractFileNameFromUrl(url));
        } catch (Exception e) {
            log.error("Failed to delete file", e);
        }
    }


    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }


    private boolean isAudioFile(MultipartFile file) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();

        boolean isAudioMime = contentType != null && (contentType.startsWith("audio/") || contentType.equals("application/octet-stream"));

        boolean isAudioExtension = fileName != null && (fileName.toLowerCase().endsWith(".mp3") || fileName.toLowerCase().endsWith(".wav") || fileName.toLowerCase().endsWith(".flac") || fileName.toLowerCase().endsWith(".ogg"));

        return isAudioMime || isAudioExtension;
    }

    private String generateImageFileName(String albumName, MultipartFile file) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String safeAlbumName = albumName.replaceAll("[^a-zA-Z0-9]", "_");
        String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));

        return String.format("%s_%s.%s", safeAlbumName, timestamp, extension);
    }


    private String generateAudioFileName(String songName, MultipartFile file) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String safeSongName = songName.replaceAll("[^a-zA-Z0-9]", "_");
        String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));

        return String.format("%s_%s.%s", safeSongName, timestamp, extension);
    }

    public static String extractFileNameFromUrl(String fullUrl) {
        String withoutProtocol = fullUrl.replaceFirst("^https?://[^/]+/", "");

        int firstSlashIndex = withoutProtocol.indexOf("/");
        if (firstSlashIndex != -1) {
            return withoutProtocol.substring(firstSlashIndex + 1);
        }

        return withoutProtocol;
    }
}
