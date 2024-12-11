package org.example.manage_web.service;

import java.util.List;

import org.example.manage_web.model.MealRecord;
import org.example.manage_web.repository.MealRecordRepository;

public class MealRecordService {
    private final MealRecordRepository mealRecordRepository;

    public MealRecordService(MealRecordRepository mealRecordRepository) {
        this.mealRecordRepository = mealRecordRepository;
    }

    public List<MealRecord> findByAuthUser_Id(Long userId) {
        return mealRecordRepository.findByAuthUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("MealRecord with user id " + userId + " not found"));
    }
}
