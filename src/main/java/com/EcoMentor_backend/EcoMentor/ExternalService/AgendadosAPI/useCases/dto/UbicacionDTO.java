package com.EcoMentor_backend.EcoMentor.ExternalService.AgendadosAPI.useCases.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UbicacionDTO {
    private int id;
    private RegionDTO region;
    private TownDTO town;
    private double latitude;
    private double longitude;
    private String address;
    private String space;
}
