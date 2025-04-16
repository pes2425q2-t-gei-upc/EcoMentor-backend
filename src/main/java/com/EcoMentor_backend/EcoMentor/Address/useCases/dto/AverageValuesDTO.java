package com.EcoMentor_backend.EcoMentor.Address.useCases.dto;


import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AverageValuesDTO {
    private Qualification nonRenewablePrimaryQualification;
    private Qualification co2Qualification;
    private Qualification heatingQualification;
    private Qualification refrigerationQualification;
    private Qualification acsQualification;
    private Qualification lightingQualification;
}
