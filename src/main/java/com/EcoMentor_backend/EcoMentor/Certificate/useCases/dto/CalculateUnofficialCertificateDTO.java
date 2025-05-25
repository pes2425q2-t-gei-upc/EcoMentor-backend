package com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto;


import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import java.time.Year;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.KindOfHeating;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.KindOfRefrigeration;
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
    private float annualCost;
    private boolean electricVehicle;
    private boolean solarThermal;
    private boolean photovoltaicSolar;
    private KindOfHeating kindOfHeating;
    private KindOfRefrigeration kindOfRefrigeration;
    private KindOfHeating kindOfAcs;
    private int insulation;
    private int windowEfficiency;
    private int residentialUseVentilation;
    private boolean energeticRehabilitation;

    private float heatingConsumption;        //en KWh anuales
    private float refrigerationConsumption;  //en KWh anuales
    private float acsConsumption;            //en KWh anuales
    private float lightingConsumption;       //en KWh anuales
}
