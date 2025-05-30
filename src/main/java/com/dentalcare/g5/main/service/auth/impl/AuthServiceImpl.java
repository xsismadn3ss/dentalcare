package com.dentalcare.g5.main.service.auth.impl;

import com.dentalcare.g5.main.service.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public String hashPassword(String password) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8)
            );
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (NoSuchAlgorithmException e){
            return  null;
        }
    }
}
