package org.example.manage_web.repository;

import java.util.Optional;

import org.example.manage_web.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);
}
