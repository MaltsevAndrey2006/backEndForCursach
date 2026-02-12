package com.dev.backendforcursach.service;

import com.dev.backendforcursach.model.Music;
import com.dev.backendforcursach.model.Purchase;
import com.dev.backendforcursach.repository.MusicRepository;
import com.dev.backendforcursach.repository.PurchaseRepository;
import com.dev.backendforcursach.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PurchaseService {
  public static final int BASIC_MUSIC_COUNTER = 1;
  private final PurchaseRepository purchaseRepository;
  private final MusicRepository musicRepository;
  private final UserRepository userRepository;

  @Transactional
  public void savePurchase(Long musicId, Long userId) {
    musicRepository.findById(musicId)
        .filter(m -> Objects.nonNull(m.getPrice()))
        .map(m -> {
          userRepository.changeBalance(userId, m.getPrice());
          musicRepository.changeCount(musicId, BASIC_MUSIC_COUNTER);
          return purchaseRepository.save(getPurchase(musicId, userId, m));
        });
  }

  //TODO in mapper
  @NotNull
  private static Purchase getPurchase(Long musicId, Long userId, Music music) {
    return Purchase.builder()
        .sum(music.getPrice())
        .userId(userId)
        .musicId(musicId)
        .build();
  }

}
