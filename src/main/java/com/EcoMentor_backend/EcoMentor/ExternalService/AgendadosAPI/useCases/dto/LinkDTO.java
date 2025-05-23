package com.EcoMentor_backend.EcoMentor.ExternalService.AgendadosAPI.useCases.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkDTO {
    private String link;
}