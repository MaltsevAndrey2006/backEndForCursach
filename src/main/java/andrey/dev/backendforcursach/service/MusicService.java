package andrey.dev.backendforcursach.service;

import andrey.dev.backendforcursach.dto.MusicRequest;
import andrey.dev.backendforcursach.dto.mapper.MusicMapper;
import andrey.dev.backendforcursach.dto.mapper.RequestMapper;
import andrey.dev.backendforcursach.models.Music;
import andrey.dev.backendforcursach.repositores.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;
    private final MusicMapper musicMapper;
    private final FileStorageService fileStorageService;
    private final RequestMapper requestMapper;

    public void createMusic(MusicRequest musicRequest) {
        musicRepository.save(musicMapper.toMusic(musicRequest, fileStorageService));
    }

    public Optional<Music> getMusicById(Long id) {
        return musicRepository.findById(id);
    }

    public List<Music> findAllMusic() {
        return musicRepository.findAll();
    }

    public void deleteMusicById(Long id) {
        Music music = musicRepository.findById(id).orElseThrow(() -> new RuntimeException("no such music with this id"));
        musicRepository.deleteById(id);
        fileStorageService.deleteMusicFile(music.getSongUrl());
        fileStorageService.deleteImageFile(music.getImgUrl());
    }

    @Transactional
    public void updateMusic(MusicRequest musicRequest, Long id) {
        Music oldMusic = musicRepository.findById(id).orElseThrow(() -> new RuntimeException("no such music with this id"));
        Music musicWithoutFiles = requestMapper.toMusic(musicRequest);
        if (musicRequest.getImg() != null) {
            fileStorageService.deleteImageFile(oldMusic.getImgUrl());
            musicWithoutFiles.setImgUrl(fileStorageService.uploadImage(musicRequest.getImg(), musicRequest.getAlbumName()));
        } else {
            musicWithoutFiles.setImgUrl(oldMusic.getImgUrl());
        }
        if (musicRequest.getSong() != null) {
            fileStorageService.deleteMusicFile(oldMusic.getSongUrl());
            musicWithoutFiles.setSongUrl(fileStorageService.uploadMusic(musicRequest.getSong(), musicRequest.getTestSongName()));
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
