package com.EcoMentor_backend.EcoMentor.Address.useCases.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTOBestQualification {
    private String addressName;
    private String addressNumber;
    private Integer zipcode;
    private String town;
    private float longitude;
    private float latitude;
    private String qualification;
}