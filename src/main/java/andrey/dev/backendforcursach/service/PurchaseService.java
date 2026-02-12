package andrey.dev.backendforcursach.service;

import andrey.dev.backendforcursach.models.Music;
import andrey.dev.backendforcursach.models.Purchase;
import andrey.dev.backendforcursach.repositores.MusicRepository;
import andrey.dev.backendforcursach.repositores.PurchaseRepository;
import andrey.dev.backendforcursach.repositores.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;

    @Transactional
    public void savePurchase(@PathVariable Long musicId, @PathVariable Long userId) {
        Music music = musicRepository.findById(musicId).orElseThrow(() -> new RuntimeException("no music with such id"));
        userRepository.changeBalance(userId, music.getPrice());
        musicRepository.changeCount(musicId, 1);
        purchaseRepository.save(new Purchase(null, null, music.getPrice(), userId, musicId));
    }

}
