package com.EcoMentor_backend.EcoMentor.ExternalService.AgendadosAPI.useCases.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImagenDTO {
    @JsonProperty("image_url")
    private String imageUrl;
}