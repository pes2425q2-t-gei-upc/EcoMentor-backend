package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;




@Service
public class GeminiService {
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String endpoint;
    private final String model;

    public GeminiService(RestTemplate restTemplate,
                         @Value("${gemini.api-key}") String apiKey,
                         @Value("${gemini.api-endpoint}") String endpoint) {
        this.restTemplate = restTemplate;
        this.apiKey       = apiKey;
        this.endpoint     = endpoint.endsWith("/") ? endpoint.substring(0, endpoint.length() - 1) : endpoint;
        this.model        = "gemini-2.0-flash";
    }

    public String generateContent(String userPrompt) {
        String url = String.format("%s/models/%s:generateContent?key=%s",
                endpoint, model, apiKey);


        Map<String, Object> part = Map.of("text", userPrompt);

        Map<String, Object> content = Map.of(
                "role", "user",
                "parts", List.of(part)
        );

        Map<String, Object> body = Map.of("contents", List.of(content));


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error: API key Invalid");
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
        if (candidates == null || candidates.isEmpty()) {
            return "";
        }
        return candidates.get(0).get("content").toString();
    }
}

