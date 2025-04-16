package com.EcoMentor_backend.EcoMentor.Address.useCases;


import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AverageValuesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
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

        int nonRenewable = 0;
        int co2 = 0;
        int heating = 0;
        int refrigeration = 0;
        int acs = 0;
        int lighting = 0;
        int numNonRenewable = 0;
        int numCo2 = 0;
        int numHeating = 0;
        int numRefrigeration = 0;
        int numAcs = 0;
        int numLighting = 0;

        for (Address address : addresses) {
            for (Certificate certificate : address.getCertificates()) {
                if (certificate.getCertificateType() == CertificateType.OFFICIAL) {
                    OfficialCertificate officialCertificate = (OfficialCertificate) certificate;

                    Integer nonRenewableValue = getNonRenewable(officialCertificate);
                    nonRenewable += nonRenewableValue;
                    numNonRenewable++;


                    Integer co2Value = getCo2(officialCertificate);
                    co2 += co2Value;
                    numCo2++;

                    Integer heatingValue = getHeating(officialCertificate);
                    heating += heatingValue;
                    numHeating++;

                    Integer refrigerationValue = getRefrigeration(officialCertificate);
                    refrigeration += refrigerationValue;
                    numRefrigeration++;


                    Integer acsValue = getAcs(officialCertificate);
                    acs += acsValue;
                    numAcs++;


                    Integer lightingValue = getLighting(officialCertificate);
                    lighting += lightingValue;
                    numLighting++;
                }
            }
        }

        return AverageValuesDTO.builder()
                .nonRenewablePrimaryQualification(numNonRenewable > 0 ? Qualification.fromValue(
                        nonRenewable / numNonRenewable) : null)
                .co2Qualification(numCo2 > 0 ? Qualification.fromValue(co2 / numCo2) : null)
                .heatingQualification(numHeating > 0 ? Qualification.fromValue(heating / numHeating) : null)
                .refrigerationQualification(numRefrigeration > 0 ? Qualification.fromValue(
                        refrigeration / numRefrigeration) : null)
                .acsQualification(numAcs > 0 ? Qualification.fromValue(acs / numAcs) : null)
                .lightingQualification(numLighting > 0 ? Qualification.fromValue(lighting / numLighting) : null)
                .build();
    }

    private Integer getNonRenewable(OfficialCertificate certificate) {
        return certificate.getNonRenewablePrimaryQualification() != null
                ? certificate.getNonRenewablePrimaryQualification().getValue()
                : 0;
    }

    private Integer getCo2(OfficialCertificate certificate) {
        return certificate.getCo2Qualification() != null
                ? certificate.getCo2Qualification().getValue()
                : 0;
    }

    private Integer getHeating(OfficialCertificate certificate) {
        return certificate.getHeatingQualification() != null
                ? certificate.getHeatingQualification().getValue()
                : 0;
    }

    private Integer getRefrigeration(OfficialCertificate certificate) {
        return certificate.getRefrigerationQualification() != null
                ? certificate.getRefrigerationQualification().getValue()
                : 0;
    }

    private Integer getAcs(OfficialCertificate certificate) {
        return certificate.getAcsQualification() != null
                ? certificate.getAcsQualification().getValue()
                : 0;
    }

    private Integer getLighting(OfficialCertificate certificate) {
        return certificate.getLightingQualification() != null
                ? certificate.getLightingQualification().getValue()
                : 0;
    }

}
