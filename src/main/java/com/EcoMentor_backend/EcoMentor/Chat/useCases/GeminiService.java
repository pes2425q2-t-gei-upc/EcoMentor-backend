package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class GeminiService {
    private final RestTemplate restTemplate;
    private final String geminiEndpoint;
    private final String apiKey;

    @Autowired
    public GeminiService(Dotenv dotenv, RestTemplate restTemplate) {
        this.apiKey = dotenv.get("GEMINI_API_KEY");
        this.geminiEndpoint = dotenv.get("GEMINI_API_ENDPOINT");
        this.restTemplate = restTemplate;
    }



}
