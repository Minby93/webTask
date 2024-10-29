package com.web.web.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EncryptionRequest {
    private String message;
    private String key;

    public EncryptionRequest(String message, String key) {
        this.message = message;
        this.key = key;
    }

}
