package com.EcoMentor_backend.EcoMentor.Address.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GraphicDTOQualification {
    String label;
    Qualification value;
}

