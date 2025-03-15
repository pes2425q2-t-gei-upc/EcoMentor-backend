package com.EcoMentor_backend.EcoMentor.Certificate.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.time.Year;

@Entity
@Table(name = "officialCertificate")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OfficialCertificate extends Certificate {

    @NotNull
    private String documentId;
    //TODO relation with address

    private String floor;

    private String door;

    @NotNull
    private String climateZone;

    @NotNull
    private Integer cadastreMeters;

    //TODO change to year class or smth?
    @NotNull
    private Year buildingYear;

    @NotNull
    private String buildingUse;

    @NotNull
    private Qualification nonRenewablePrimaryQualification;

    @NotNull
    private float nonRenewablePrimaryEnergy;

    @NotNull
    private Qualification co2Qualification;

    @NotNull
    private float co2Emissions;

    @NotNull
    private float finalEnergyConsumption;

    @NotNull
    private float annualCost;

    @NotNull
    private boolean electricVehicle;

    @NotNull
    private boolean solarThermal;

    @NotNull
    private boolean photovoltaicSolar;

    @NotNull
    private boolean biomass;

    @NotNull
    private boolean districtNet;

    @NotNull
    private boolean geothermal;

    @NotNull
    private float insulation;

    @NotNull
    private float windowEfficiency;

    @NotNull
    private Qualification heatingQualification;

    @NotNull
    private float heatingEmissions;

    @NotNull
    private Qualification refrigerationQualification;

    @NotNull
    private float refrigerationEmissions;

    @NotNull
    private Qualification acsQualification;

    @NotNull
    private float acsEmissions;

    @NotNull
    private Qualification lightingQualification;

    @NotNull
    private float lightingEmissions;

    @NotNull
    private float residentialUseVentilation;

    @NotNull
    private boolean energeticRehabilitation;

    @NotNull
    private Date entryDate;
}
