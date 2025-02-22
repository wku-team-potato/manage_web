package org.example.manage_web.repository;

import java.util.List;
import java.util.Optional;

import org.example.manage_web.model.PurchaseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRecordRepository extends JpaRepository<PurchaseRecord, Long> {
    Optional<List<PurchaseRecord>> findByUser_id(Long userId);

    Optional<List<PurchaseRecord>> findByItem_id(Long itemId);
}
