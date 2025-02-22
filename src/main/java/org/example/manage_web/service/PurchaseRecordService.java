package org.example.manage_web.service;

import java.util.List;

import org.example.manage_web.model.PurchaseRecord;
import org.example.manage_web.repository.PurchaseRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseRecordService {
    private final PurchaseRecordRepository purchaseRecordRepository;

    public PurchaseRecordService(PurchaseRecordRepository purchaseRecordRepository) {
        this.purchaseRecordRepository = purchaseRecordRepository;
    }

    public List<PurchaseRecord> findByUser_id(Long userId) {
        return purchaseRecordRepository.findByUser_id(userId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Purchase record with user id " + userId + " not found"));
    }

    public List<PurchaseRecord> findByItem_id(Long itemId) {
        return purchaseRecordRepository.findByItem_id(itemId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Purchase record with item id " + itemId + " not found"));
    }
}
