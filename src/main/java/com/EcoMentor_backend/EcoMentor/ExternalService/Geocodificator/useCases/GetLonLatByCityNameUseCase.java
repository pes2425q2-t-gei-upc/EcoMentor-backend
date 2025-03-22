package com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases;

import com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.dto.CoordinatesDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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

    public List<CoordinatesDTO> execute(String cityName, int size) {
        String url = "https://eines.icgc.cat/geocodificador/cerca?text=" + URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        List<CoordinatesDTO> coordinatesList = new ArrayList<>();

        try {
            if (response.getStatusCode().isError()) {
                return null;
            }


            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode features = root.path("features");


            if (features.isArray()) {
                for (int i = 0; i < features.size() && coordinatesList.size() < size; i++) {
                    JsonNode feature = features.get(i);
                    JsonNode coordinates = feature.path("geometry").path("coordinates");
                    JsonNode properties = feature.path("properties");


                    if (coordinates.size() >= 2) {
                        float longitude = (float) coordinates.get(0).asDouble();
                        float latitude = (float) coordinates.get(1).asDouble();


                        String name = properties.path("nom").asText();

                        coordinatesList.add(new CoordinatesDTO(name, latitude, longitude));
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }

        return coordinatesList;
    }
}
