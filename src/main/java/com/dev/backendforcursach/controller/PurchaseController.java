package com.dev.backendforcursach.controller;

import com.dev.backendforcursach.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/purchases")
public class PurchaseController {
  private final PurchaseService purchaseService;

  @PostMapping("{userId}/music/{musicId}")
  public void transact(@PathVariable Long userId,
                       @PathVariable Long musicId) {
    purchaseService.savePurchase(musicId, userId);
  }


}
