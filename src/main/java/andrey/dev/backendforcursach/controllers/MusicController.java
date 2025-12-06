package andrey.dev.backendforcursach.controllers;

import andrey.dev.backendforcursach.dto.MusicRequest;
import andrey.dev.backendforcursach.models.Music;
import andrey.dev.backendforcursach.service.MusicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/music")
@RequiredArgsConstructor
@Tag(name = "Music Controller", description = "Контроллер для управления музыкой")
public class MusicController {
    private final MusicService musicService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadMusic(
            @RequestParam("albumName") String albumName,
            @RequestParam("groupName") String groupName,
            @RequestParam("price") BigDecimal price,
            @RequestParam("count") Integer count,
            @RequestParam("description") String description,
            @RequestParam("releaseDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseDate,
            @RequestParam("testSongName") String testSongName,
            @RequestPart("imgFile") MultipartFile imgFile,
            @RequestPart("musicFile") MultipartFile musicFile
    ) {
        musicService.createMusic(
                new MusicRequest(albumName
                        , groupName
                        , price
                        , count
                        , description
                        , releaseDate
                        , imgFile
                        , musicFile
                        , testSongName));
        return ResponseEntity.ok("OK!");
    }

    @GetMapping("{id}")
    public ResponseEntity<Music> geMusic(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(musicService.getMusicById(id).orElseThrow(() -> new RuntimeException("no such id for music")));
    }

    @GetMapping
    public ResponseEntity<List<Music>> getAllMusic() {
        return ResponseEntity.ok(musicService.findAllMusic());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMusic(Long id) {
        musicService.deleteMusicById(id);
        return ResponseEntity.ok("OK!");
    }

    @PutMapping(path = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateMusic(
            @PathVariable Long id,
            @RequestParam("albumName") String albumName,
            @RequestParam("groupName") String groupName,
            @RequestParam("price") BigDecimal price,
            @RequestParam("count") Integer count,
            @RequestParam("description") String description,
            @RequestParam("releaseDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate releaseDate,
            @RequestParam("testSongName") String testSongName,
            @RequestPart("imgFile") MultipartFile imgFile,
            @RequestPart("musicFile") MultipartFile musicFile) {
        musicService.updateMusic(new MusicRequest(albumName
                , groupName
                , price
                , count
                , description
                , releaseDate
                , imgFile
                , musicFile
                , testSongName), id);
        return ResponseEntity.ok("Ok");
    }


}