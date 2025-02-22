package org.example.manage_web.repository;

import org.example.manage_web.model.HeightWeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HeightWeightRecordRepository extends JpaRepository<HeightWeightRecord, Long> {
    Optional<List<HeightWeightRecord>> findByAuthUser_Id(Long userId);
}
