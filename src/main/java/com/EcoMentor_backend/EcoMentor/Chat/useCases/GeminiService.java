package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class GeminiService {
    private final RestTemplate restTemplate;
    private final String geminiEndpoint;
    private final String apiKey;

    public GeminiService(RestTemplate restTemplate,
                         @Value("${gemini.api-endpoint}") String geminiEndpoint,
                         @Value("${gemini.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.geminiEndpoint = geminiEndpoint;
        this.apiKey = apiKey;
    }



}
