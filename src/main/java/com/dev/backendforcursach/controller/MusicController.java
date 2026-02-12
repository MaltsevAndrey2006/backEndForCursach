package com.dev.backendforcursach.controller;

import com.dev.backendforcursach.model.Music;
import com.dev.backendforcursach.model.dto.MusicRequest;
import com.dev.backendforcursach.service.MusicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/musics")
@RequiredArgsConstructor
@Tag(name = "Music Controller", description = "Контроллер для управления музыкой")
public class MusicController {
  private final MusicService musicService;

  @GetMapping("/sorted/price-asc")
  public List<Music> findMusicSortedByPriceABS() {
    return musicService.findMusicSortedByPriceABS();
  }

  @GetMapping("/sorted/price-desc")
  public List<Music> findMusicSortedByPriceDESC() {
    return musicService.findMusicSortedByPriceDESC();
  }

  @GetMapping("/sorted/date-asc")
  public List<Music> findMusicSortedByReleaseDateABS() {
    return musicService.findMusicSortedByReleaseDateABS();
  }

  @GetMapping("/sorted/date-desc")
  public List<Music> findMusicSortedByReleaseDateDesc() {
    return musicService.findMusicSortedByReleaseDateDesc();
  }

  @GetMapping("/search")
  public List<Music> getMusicsWithSearch(@RequestParam String search) {
    return musicService.findAllMusicWithSearch(search);
  }

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void uploadMusic(
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
        new MusicRequest(albumName,
            groupName,
            price,
            count,
            description,
            releaseDate,
            imgFile,
            musicFile,
            testSongName));
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

  @DeleteMapping("{id}")
  public void deleteMusic(@PathVariable Long id) {
    musicService.deleteMusicById(id);
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
      @RequestPart(value = "imgFile", required = false) MultipartFile imgFile,
      @RequestPart(value = "musicFile", required = false) MultipartFile musicFile) {
    musicService.updateMusic(new MusicRequest(albumName,
        groupName,
        price,
        count,
        description,
        releaseDate,
        imgFile,
        musicFile,
        testSongName), id);
    return ResponseEntity.ok("Ok");
  }


}