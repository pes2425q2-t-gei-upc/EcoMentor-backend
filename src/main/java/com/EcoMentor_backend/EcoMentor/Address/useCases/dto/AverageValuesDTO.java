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
    private float finalEnergyConsumption;
    private float nonRenewablePrimaryEmissions;
    private float co2Emissions;
    private float heatingEmissions;
    private float refrigerationEmissions;
    private float acsEmissions;
    private float lightingEmissions;
    private float insulation;
    private float windowEfficiency;
}
