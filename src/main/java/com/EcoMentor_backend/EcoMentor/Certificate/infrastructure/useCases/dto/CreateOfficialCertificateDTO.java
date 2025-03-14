package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateOfficialCertificateDTO extends CreateCertificateDTO {
    private String documentId;


    private String floor;

    private String door;


    private String climateZone;


    private Integer cadastreMeters;

    private Year buildingYear;


    private String buildingUse;


    private Qualification nonRenewablePrimaryQualification;


    private float nonRenewablePrimaryEnergy;


    private Qualification co2Qualification;


    private float co2Emissions;


    private float finalEnergyConsumption;


    private float annualCost;


    private boolean electricVehicle;


    private boolean solarThermal;


    private boolean photovoltaicSolar;


    private boolean biomass;


    private boolean districtNet;


    private boolean geothermal;


    private float insulation;


    private float windowEfficiency;


    private Qualification heatingQualification;


    private float heatingEmissions;


    private Qualification refrigerationQualification;


    private float refrigerationEmissions;


    private Qualification acsQualification;


    private float acsEmissions;


    private Qualification lightingQualification;


    private float lightingEmissions;


    private float residentialUseVentilation;


    private boolean energeticRehabilitation;


    private Date entryDate;
}
