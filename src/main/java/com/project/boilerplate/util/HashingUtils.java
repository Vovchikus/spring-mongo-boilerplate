package com.project.boilerplate.util;

import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashingUtils {
    private static final int workload = 10;

    public static String encryptPassword(String password) {
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(password, salt);
    }

    public static String generateSHA256AuthToken(String email, String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String hashable = email + password;
        byte[] hash = digest.digest(hashable.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

}
