package com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculatorResultsDTO {
    Qualification nonRenewablePrimaryQualification;
    Qualification co2Qualification;
    Qualification heatingQualification;
    Qualification refrigerationQualification;
    Qualification acsQualification;
    Qualification lightingQualification;
}
