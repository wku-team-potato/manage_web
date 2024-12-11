package org.example.manage_web.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.util.Base64;

public class PasswordHasher {

    public static boolean checkPassword(String rawPassword, String encodedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 1. Split the encoded password
        String[] parts = encodedPassword.split("\\$");
        // String algorithm = parts[0]; // "pbkdf2_sha256"
        int iterations = Integer.parseInt(parts[1]); // e.g., 600000
        String salt = parts[2]; // Salt value
        String hash = parts[3]; // Hashed password

        // 2. Generate hash for the raw password using the same algorithm, salt, and
        // iterations
        String generatedHash = hashPassword(rawPassword, salt, iterations);

        // 3. Compare the generated hash with the stored hash
        return hash.equals(generatedHash);
    }

    public static String hashPassword(String password, String salt, int iterations)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Convert salt to bytes
        byte[] saltBytes = salt.getBytes();

        // Create PBEKeySpec with provided password, salt, iterations, and key length
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, 256);

        // Use PBKDF2WithHmacSHA256 to generate the key
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = keyFactory.generateSecret(spec).getEncoded();

        // Return the Base64-encoded hash
        return Base64.getEncoder().encodeToString(hash);
    }

}
