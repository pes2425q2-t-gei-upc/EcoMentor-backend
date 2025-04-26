package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

class GeminiServiceTest {

private RestTemplate restTemplate;
private GeminiService geminiService;

@BeforeEach
void setUp() {
    restTemplate = mock(RestTemplate.class);
    geminiService = new GeminiService(restTemplate, "test-api-key", "http://test-endpoint");
}

@Nested
@DisplayName("generateContent")
class GenerateContent {

    @Test
    @DisplayName("Returns content when API response is successful and contains candidates")
    void returnsContentWhenApiResponseIsSuccessful() {
        Map<String, Object> responseBody = Map.of(
                "candidates", List.of(Map.of("content", Map.of("text", "Generated content")))
        );
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class))).thenReturn(responseEntity);

        String result = geminiService.generateContent(List.of(Map.of("key", "value")));

        assertEquals("Generated content", result);
    }

    @Test
    @DisplayName("Returns empty string when API response contains no candidates")
    void returnsEmptyStringWhenNoCandidates() {
        Map<String, Object> responseBody = Map.of("candidates", List.of());
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class))).thenReturn(responseEntity);

        String result = geminiService.generateContent(List.of(Map.of("key", "value")));

        assertEquals("", result);
    }

    @Test
    @DisplayName("Throws exception when API response is not successful")
    void throwsExceptionWhenApiResponseIsNotSuccessful() {
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class))).thenReturn(responseEntity);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                geminiService.generateContent(List.of(Map.of("key", "value")))
        );

        assertEquals("Error: API key inválida", exception.getMessage());
    }

    @Test
    @DisplayName("Returns concatenated text when content has nested parts")
    void returnsConcatenatedTextForNestedParts() {
        Map<String, Object> responseBody = Map.of(
                "candidates", List.of(Map.of("content", Map.of("parts", List.of(
                        Map.of("text", "Part 1"),
                        Map.of("text", "Part 2")
                ))))
        );
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class))).thenReturn(responseEntity);

        String result = geminiService.generateContent(List.of(Map.of("key", "value")));

        assertEquals("Part 1Part 2", result);
    }
}
}
