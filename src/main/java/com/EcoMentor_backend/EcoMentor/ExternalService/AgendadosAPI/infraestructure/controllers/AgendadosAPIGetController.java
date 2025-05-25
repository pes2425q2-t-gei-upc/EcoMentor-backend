package com.EcoMentor_backend.EcoMentor.ExternalService.AgendadosAPI.infraestructure.controllers;


import com.EcoMentor_backend.EcoMentor.ExternalService.AgendadosAPI.useCases.GetEventUseCase;
import com.EcoMentor_backend.EcoMentor.ExternalService.AgendadosAPI.useCases.dto.EventoDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Validated
@RequestMapping("/api/agendados")
public class AgendadosAPIGetController {

    private final GetEventUseCase getEventUseCase;

    public AgendadosAPIGetController(GetEventUseCase getEventUseCase) {
        this.getEventUseCase = getEventUseCase;
    }

    @GetMapping
    public ResponseEntity<List<EventoDTO>> getEvents(@RequestParam double latMin, @RequestParam double latMax,
                                                     @RequestParam double lonMin, @RequestParam double lonMax) {
        List<EventoDTO> events = getEventUseCase.execute(latMin, latMax, lonMin, lonMax);
        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(events);
        }
    }


}
