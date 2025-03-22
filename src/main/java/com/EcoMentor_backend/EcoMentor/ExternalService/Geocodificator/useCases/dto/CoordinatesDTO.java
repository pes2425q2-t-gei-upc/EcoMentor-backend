package com.EcoMentor_backend.EcoMentor.ExternalService.Geocodificator.useCases.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoordinatesDTO {
    private float longitude;
    private float latitude;
}
