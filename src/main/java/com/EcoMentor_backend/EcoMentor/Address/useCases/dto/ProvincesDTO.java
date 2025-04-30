package com.EcoMentor_backend.EcoMentor.Address.useCases.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvincesDTO {
    private List<String> provinces;
}

