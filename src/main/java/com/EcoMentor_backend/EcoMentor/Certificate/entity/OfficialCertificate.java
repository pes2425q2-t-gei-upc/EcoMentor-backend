package com.EcoMentor_backend.EcoMentor.Certificate.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.time.Year;

@Entity
@Table(name = "officialCertificate")
public class OfficialCertificate extends Certificate {

    @NotNull
    private String documentId;
    //TODO relation with address
    @NotNull
    private String addressId;

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
    private String nonRenewablePrimaryQualification;

    @NotNull
    private float nonRenewablePrimaryEnergy;

    @NotNull
    private String co2Qualification;

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
    private String heatingQualification;

    @NotNull
    private float heatingEmissions;

    @NotNull
    private String refrigerationQualification;

    @NotNull
    private float refrigerationEmissions;

    @NotNull
    private String acsQualification;

    @NotNull
    private float acsEmissions;

    @NotNull
    private String lightingQualification;

    @NotNull
    private float lightingEmissions;

    @NotNull
    private float residentialUseVentilation;

    @NotNull
    private boolean energeticRehabilitation;

    @NotNull
    private Date entryDate;
}
