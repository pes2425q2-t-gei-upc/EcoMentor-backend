package com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto;


import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import java.time.Year;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculateUnofficialCertificateDTO {

    private CreateAddressDTO createAddressDTO;

    private String floor;
    private String door;
    private String climateZone;
    private Year buildingYear;
    private String buildingUse;
    private float cadastreMeters;
    private int nonRenewablePrimaryEnergyAprox;
    private float finalEnergyConsumption;
    private float annualCost;
    private boolean electricVehicle;
    private boolean solarThermal;
    private boolean photovoltaicSolar;
    private boolean biomass;
    private boolean districtNet;
    private boolean geothermal;
    private int insulation;
    private int windowEfficiency;
    private int heatingEmissionsAprox;
    private int refrigerationEmissionsAprox;
    private int acsEmissionsAprox;
    private int lightingEmissionsAprox;
    private int residentialUseVentilation;
    private boolean energeticRehabilitation;
}
