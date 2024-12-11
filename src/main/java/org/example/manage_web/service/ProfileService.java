package org.example.manage_web.service;

import java.util.List;
import java.util.Optional;

import org.example.manage_web.model.Profile;
import org.example.manage_web.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Optional<Profile> findByUserId(Long userId) {
        return profileRepository.findByAuthUser_Id(userId);
    }
}
