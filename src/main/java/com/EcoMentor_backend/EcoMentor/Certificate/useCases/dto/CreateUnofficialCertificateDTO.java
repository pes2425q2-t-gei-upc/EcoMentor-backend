package com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto;


import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import java.sql.Date;
import java.time.Year;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateUnofficialCertificateDTO extends CreateCertificateDTO {
    private String floor;
    private String door;
    private String climateZone;
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
    private Date creationDate;
}
