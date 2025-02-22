package org.example.manage_web.service;

import java.util.Optional;

import org.example.manage_web.model.AuthUser;
import org.example.manage_web.repository.AuthUserRepository;
import org.example.manage_web.utils.PasswordHasher;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {
    private final AuthUserRepository authUserRepository;

    public AuthUserService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    public Optional<AuthUser> findByUsername(String username) {
        return authUserRepository.findByUsername(username);
    }

    public Boolean deleteUserById(Long id) {
        try {
            if (authUserRepository.existsById(id)) {
                authUserRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean checkPassword(String rawPassword, String encodedPassword) {
        try {
            return PasswordHasher.checkPassword(rawPassword, encodedPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
