package org.example.manage_web.repository;

import java.util.Optional;

import org.example.manage_web.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByAuthUser_Id(Long userId);
}