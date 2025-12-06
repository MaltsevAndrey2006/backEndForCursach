package andrey.dev.backendforcursach.service;

import andrey.dev.backendforcursach.dto.MusicRequest;
import andrey.dev.backendforcursach.dto.mapper.MusicMapper;
import andrey.dev.backendforcursach.models.Music;
import andrey.dev.backendforcursach.repositores.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;
    private final MusicMapper musicMapper;
    private final FileStorageService fileStorageService;

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
        fileStorageService.deleteImageFile(oldMusic.getImgUrl());
        fileStorageService.deleteMusicFile(oldMusic.getSongUrl());
        Music changedMusic = musicMapper.toMusic(musicRequest, fileStorageService);
        musicRepository.updateMusic(changedMusic, id);
    }

}
