package org.example.manage_web.repository;

import java.util.List;
import java.util.Optional;

import org.example.manage_web.model.MealRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRecordRepository extends JpaRepository<MealRecord, Long> {
    Optional<List<MealRecord>> findByAuthUser_Id(Long userId);
}