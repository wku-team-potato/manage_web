package org.example.manage_web.service;

import org.example.manage_web.model.Food;
import org.example.manage_web.repository.FoodRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Page<Food> findAllByPage(int page, int size) {
        return foodRepository.findAll(PageRequest.of(page, size));
    }

}
