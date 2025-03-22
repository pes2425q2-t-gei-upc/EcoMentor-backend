package com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases;

import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.dto.CoordinatesDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
@Transactional
public class GetLonLatByCityNameUseCase {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Para leer JSON

    public GetLonLatByCityNameUseCase(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CoordinatesDTO execute(String cityName) {
        String url = "https://eines.icgc.cat/geocodificador/cerca?text=" + URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        try {
            if (response.getStatusCode().isError()) {
                return null;
            }
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode features = root.path("features");

            if (features.isArray() && !features.isEmpty()) {
                JsonNode firstFeature = features.get(0);
                JsonNode coordinates = firstFeature.path("geometry").path("coordinates");

                if (coordinates.size() >= 2) {
                    float longitude = (float) coordinates.get(0).asDouble();
                    float latitude = (float) coordinates.get(1).asDouble();
                    return new CoordinatesDTO(longitude, latitude);
                }
            }
        } catch (Exception e) {
            return null; // O lanzar una excepción personalizada
        }

        return null;
    }


}
