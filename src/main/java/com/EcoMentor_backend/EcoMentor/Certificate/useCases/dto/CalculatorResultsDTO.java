package com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculatorResultsDTO {

    Long certificateId;
    float ioNonRenewablePrimaryEnergy;
    float ioCO2E;
    float ioHeating;
    float ioRefrigeration;
    float ioACS;
    float ioLighting;
    Qualification nonRenewablePrimaryQualification;
    Qualification co2Qualification;
    Qualification heatingQualification;
    Qualification refrigerationQualification;
    Qualification acsQualification;
    Qualification lightingQualification;
}
