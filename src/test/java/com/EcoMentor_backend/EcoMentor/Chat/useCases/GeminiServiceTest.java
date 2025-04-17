package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

class GeminiServiceTest {

@Mock
private RestTemplate restTemplate;

private GeminiService geminiService;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
    geminiService = new GeminiService(restTemplate, "test-api-key", "http://test-endpoint");
}

@Test
@DisplayName("generateContent returns content when API call is successful")
void generateContentReturnsContentWhenApiCallIsSuccessful() {
    Map<String, Object> mockResponse = Map.of(
            "candidates", List.of(Map.of("content", "Generated content"))
    );
    when(restTemplate.postForEntity(anyString(), any(), eq(Map.class)))
            .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

    String result = geminiService.generateContent("Test prompt");

    assertEquals("Generated content", result);
}

@Test
@DisplayName("generateContent throws exception when API call fails")
void generateContentThrowsExceptionWhenApiCallFails() {
    when(restTemplate.postForEntity(anyString(), any(), eq(Map.class)))
            .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
            geminiService.generateContent("Test prompt"));

    assertTrue(exception.getMessage().contains("Error"));
}


@Test
@DisplayName("generateContent returns empty string when candidates list is empty")
void generateContentReturnsEmptyStringWhenCandidatesListIsEmpty() {
    Map<String, Object> mockResponse = Map.of("candidates", List.of());
    when(restTemplate.postForEntity(anyString(), any(), eq(Map.class)))
            .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

    String result = geminiService.generateContent("Test prompt");

    assertEquals("", result);
}
}