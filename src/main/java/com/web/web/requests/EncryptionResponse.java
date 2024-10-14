package com.web.web.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EncryptionResponse {
    @Override
    public String toString() {
        return "EncryptionResponse{" +
                "encryptedMessage='" + encryptedMessage + '\'' +
                '}';
    }

    private String encryptedMessage;
}
