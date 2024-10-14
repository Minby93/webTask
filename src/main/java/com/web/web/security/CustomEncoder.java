package com.web.web.security;

import com.web.web.services.EncryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomEncoder implements PasswordEncoder {
    @Value("${key}")
    String key;

    private final EncryptService encryptService;

    @Autowired
    public CustomEncoder(EncryptService encryptService) {
        this.encryptService = encryptService;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return encryptService.encryptMessage(rawPassword.toString(), key);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String decryptedPassword = encryptService.decodeMessage(encodedPassword, key);
        return decryptedPassword.equals(rawPassword.toString());
    }
}
