package com.EcoMentor_backend.EcoMentor.Auth.useCases.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleAuthRequestDTO {
    private String idToken;
}
