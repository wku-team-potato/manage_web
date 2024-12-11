package org.example.manage_web.service;

import java.util.List;
import java.util.Optional;

import org.example.manage_web.repository.HeightWeightRecordRepository;
import org.springframework.stereotype.Service;
import org.example.manage_web.model.HeightWeightRecord;

@Service
public class HeightWeightService {
    private final HeightWeightRecordRepository heightWeightRecordRepository;

    public HeightWeightService(HeightWeightRecordRepository heightWeightRecordRepository) {
        this.heightWeightRecordRepository = heightWeightRecordRepository;
    }

    public Optional<List<HeightWeightRecord>> findByUserId(Long userId) {
        return heightWeightRecordRepository.findByAuthUser_Id(userId);
    }
}
