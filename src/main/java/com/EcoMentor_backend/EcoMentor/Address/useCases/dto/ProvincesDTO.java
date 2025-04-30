package com.EcoMentor_backend.EcoMentor.Address.useCases.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvincesDTO {
    private ArrayList<String> provinces;
}

