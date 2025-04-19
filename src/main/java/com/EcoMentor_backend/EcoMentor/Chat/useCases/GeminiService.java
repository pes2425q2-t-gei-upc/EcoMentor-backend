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
        this.endpoint     = endpoint.endsWith("/")
                ? endpoint.substring(0, endpoint.length() - 1)
                : endpoint;
        this.model        = "gemini-2.0-flash";
    }

    public String generateContent(List<Map<String, Object>> contents) {
        String url = String.format("%s/models/%s:generateContent?key=%s",
                endpoint, model, apiKey);

        Map<String, Object> body = Map.of("contents", contents);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error: API key inválida");
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> candidates =
                (List<Map<String, Object>>) response.getBody().get("candidates");

        if (candidates.isEmpty()) {
            return "";
        }

        // Obtenemos el objeto "content" del primer candidato
        Object contentObj = candidates.get(0).get("content");
        // Extraemos únicamente el texto
        return extractText(contentObj);
    }

    /**
     * Recorre recursivamente la estructura devuelta por Gemini
     * y concatena todos los valores de texto.
     */
    @SuppressWarnings("unchecked")
    private String extractText(Object node) {
        if (node instanceof String) {
            return (String) node;
        }

        if (node instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) node;
            // Si tiene clave "text", descendemos sobre ella
            if (map.containsKey("text")) {
                return extractText(map.get("text"));
            }
            // Si tiene clave "parts", iteramos y concatenamos
            if (map.containsKey("parts")) {
                StringBuilder sb = new StringBuilder();
                List<Object> parts = (List<Object>) map.get("parts");
                for (Object part : parts) {
                    sb.append(extractText(part));
                }
                return sb.toString();
            }
        }

        if (node instanceof List) {
            StringBuilder sb = new StringBuilder();
            for (Object elem : (List<Object>) node) {
                sb.append(extractText(elem));
            }
            return sb.toString();
        }

        // Si no es ninguno de los anteriores, devolvemos cadena vacía
        return "";
    }
}
