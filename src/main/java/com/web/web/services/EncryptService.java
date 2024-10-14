package com.web.web.services;

import com.web.web.requests.EncryptionRequest;
import com.web.web.requests.EncryptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class EncryptService {

    private final RestTemplate restTemplate;

    @Autowired
    public EncryptService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String encryptMessage(String message, String key) {
        String url = "http://localhost:5000/encrypt"; // URL вашего API

        // Создаем объект для отправки в запросе
        EncryptionRequest request = new EncryptionRequest(message, key);

        // Отправляем POST-запрос
        EncryptionResponse response = restTemplate.postForObject(url, request, EncryptionResponse.class);

        return Objects.requireNonNull(response).getEncryptedMessage();
    }

    public String decodeMessage(String message, String key) {
        String url = "http://localhost:5000/decode"; // URL вашего API

        // Создаем объект для отправки в запросе
        EncryptionRequest request = new EncryptionRequest(message, key);

        // Отправляем POST-запрос
        EncryptionResponse response = restTemplate.postForObject(url, request, EncryptionResponse.class);

        return Objects.requireNonNull(response).getEncryptedMessage();
    }
}



