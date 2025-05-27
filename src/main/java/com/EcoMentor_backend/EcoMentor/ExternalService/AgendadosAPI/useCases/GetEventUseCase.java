package com.EcoMentor_backend.EcoMentor.ExternalService.AgendadosAPI.useCases;

import com.EcoMentor_backend.EcoMentor.ExternalService.AgendadosAPI.useCases.dto.EventoDTO;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
@Transactional
public class GetEventUseCase {

    private final RestTemplate restTemplate;

    public GetEventUseCase(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<EventoDTO> execute(double latMin, double latMax, double lonMin, double lonMax) {
        String url = "https://agendados-backend-842309366027.europe-southwest1.run.app" + "/api/eventsInArea?lat_min="
                + latMin + "&lat_max=" + latMax + "&lon_min=" + lonMin + "&lon_max=" + lonMax;
        ResponseEntity<EventoDTO[]> response = restTemplate.getForEntity(url, EventoDTO[].class);


        List<EventoDTO> lista;

        try {
            if (response.getStatusCode().isError()) {
                return null;
            }

            EventoDTO[] eventos = response.getBody();
            if (eventos == null) {
                return null;
            }
            lista = Arrays.asList(eventos);

        } catch (Exception e) {
            return null;
        }

        return lista;

    }

}
