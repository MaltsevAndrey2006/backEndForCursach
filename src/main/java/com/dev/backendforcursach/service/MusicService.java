package com.dev.backendforcursach.service;

import com.dev.backendforcursach.enums.FileType;
import com.dev.backendforcursach.mapper.MusicMapper;
import com.dev.backendforcursach.mapper.RequestMapper;
import com.dev.backendforcursach.model.Music;
import com.dev.backendforcursach.model.dto.MusicRequest;
import com.dev.backendforcursach.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MusicService {
  private final MusicRepository musicRepository;
  private final MusicMapper musicMapper;
  private final FileStorageService fileStorageService;
  private final RequestMapper requestMapper;

  public void createMusic(MusicRequest musicRequest) {
    Optional.ofNullable(musicRequest)
        .map(musicMapper::toMusic)
        .ifPresent(musicRepository::save);
  }

  public Optional<Music> getMusicById(Long id) {
    return musicRepository.findById(id);
  }

  public List<Music> findAllMusic() {
    return musicRepository.findAll();
  }

  public void deleteMusicById(Long id) {
    var music = musicRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(String.format("No such music with this id: %s", id)));
    musicRepository.deleteById(id);
    fileStorageService.deleteFile(music.getSongUrl(), FileType.SONG);
    fileStorageService.deleteFile(music.getImgUrl(), FileType.IMAGE);
  }

  @Transactional
  public void updateMusic(MusicRequest musicRequest, Long id) {
    Music oldMusic = musicRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No such music with this id"));

    Music musicWithoutFiles = requestMapper.toMusic(musicRequest);

    if (musicRequest.getImg() != null) {
      fileStorageService.deleteFile(oldMusic.getImgUrl(), FileType.IMAGE);
      musicWithoutFiles.setImgUrl(fileStorageService.uploadFile(musicRequest.getImg(), musicRequest.getAlbumName(), FileType.SONG));
    } else {
      musicWithoutFiles.setImgUrl(oldMusic.getImgUrl());
    }
    if (musicRequest.getSong() != null) {
      fileStorageService.deleteFile(oldMusic.getSongUrl(), FileType.SONG);
      musicWithoutFiles.setSongUrl(fileStorageService.uploadFile(musicRequest.getSong(), musicRequest.getTestSongName(), FileType.SONG));
    } else {
      musicWithoutFiles.setSongUrl(oldMusic.getSongUrl());
    }
    musicRepository.updateMusic(musicWithoutFiles, id);
  }

  public List<Music> findAllMusicWithSearch(String search) {
    var res = search.split(" ");
    Set<Music> combinedSet = new LinkedHashSet<>();
    for (String s : res) {
      combinedSet.addAll(musicRepository.findAllMusicsWithSearch(s));
    }
    return new ArrayList<>(combinedSet);
  }

  @Transactional
  public void changeCount(Long id, Integer count) {
    musicRepository.changeCount(id, count);
  }

  public List<Music> findMusicSortedByPriceABS() {
    return musicRepository.findAllSortedByPriceABSMusics();
  }

  public List<Music> findMusicSortedByPriceDESC() {
    return musicRepository.findAllSortedByPriceDESCMusics();
  }

  public List<Music> findMusicSortedByReleaseDateABS() {
    return musicRepository.findAllSortedByReleaseDateABSMusics();
  }

  public List<Music> findMusicSortedByReleaseDateDesc() {
    return musicRepository.findAllSortedByReleaseDateDESCMusics();
  }

}
