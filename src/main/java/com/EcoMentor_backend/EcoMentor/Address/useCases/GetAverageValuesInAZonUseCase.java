package com.EcoMentor_backend.EcoMentor.Address.useCases;


import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AverageValuesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@AllArgsConstructor
public class GetAverageValuesInAZonUseCase {
    private final AddressRepository addressRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public AverageValuesDTO execute(Double latitude, Double longitude, Integer radius) {
        List<Address> addresses = new ArrayList<>();
        if (latitude != null && longitude != null && radius != null) {
            radius = radius * 1000;
            Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
            List<Address> nearbyAddresses = addressRepository.findAddressesWithinDistance(location, radius);
            if (nearbyAddresses.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No addresses found within the specified radius");
            }
            addresses.addAll(nearbyAddresses);
        }

        float insulation = 0;
        float nonRenewable = 0;
        float co2 = 0;
        float heating = 0;
        float finalEnergyConsumption = 0;
        float refrigeration = 0;
        float acs = 0;
        float lighting = 0;
        float windowEfficiency = 0;
        float numInsulation = 0;
        float numNonRenewable = 0;
        float numCo2 = 0;
        float numHeating = 0;
        float numRefrigeration = 0;
        float numAcs = 0;
        float numLighting = 0;
        float numFinalEnergyConsumption = 0;
        float numWindowEfficiency = 0;

        for (Address address : addresses) {
            for (Certificate certificate : address.getCertificates()) {
                if (certificate.getCertificateType() == CertificateType.OFFICIAL) {
                    OfficialCertificate officialCertificate = (OfficialCertificate) certificate;

                    float insulationValue = officialCertificate.getInsulation();
                    insulation += insulationValue;
                    numInsulation++;

                    float nonRenewableValue = officialCertificate.getNonRenewablePrimaryEnergy();
                    nonRenewable += nonRenewableValue;
                    numNonRenewable++;

                    float finalEnergyConsumptionValue = officialCertificate.getFinalEnergyConsumption();
                    finalEnergyConsumption += finalEnergyConsumptionValue;
                    numFinalEnergyConsumption++;

                    float co2Value = officialCertificate.getCo2Emissions();
                    co2 += co2Value;
                    numCo2++;

                    float heatingValue = officialCertificate.getHeatingEmissions();
                    heating += heatingValue;
                    numHeating++;

                    float refrigerationValue = officialCertificate.getRefrigerationEmissions();
                    refrigeration += refrigerationValue;
                    numRefrigeration++;


                    float acsValue = officialCertificate.getAcsEmissions();
                    acs += acsValue;
                    numAcs++;


                    float lightingValue = officialCertificate.getLightingEmissions();
                    lighting += lightingValue;
                    numLighting++;

                    float windowEfficiencyValue = officialCertificate.getWindowEfficiency();
                    windowEfficiency += windowEfficiencyValue;
                    numWindowEfficiency++;
                }
            }
        }

        return AverageValuesDTO.builder()
                .insulation(insulation / numInsulation)
                .finalEnergyConsumption(finalEnergyConsumption / numFinalEnergyConsumption)
                .nonRenewablePrimaryEmissions(nonRenewable / numNonRenewable)
                .co2Emissions(co2 / numCo2)
                .heatingEmissions(heating / numHeating)
                .refrigerationEmissions(refrigeration / numRefrigeration)
                .acsEmissions(acs / numAcs)
                .lightingEmissions(lighting / numLighting)
                .windowEfficiency(windowEfficiency / numWindowEfficiency)
                .build();
    }

}
