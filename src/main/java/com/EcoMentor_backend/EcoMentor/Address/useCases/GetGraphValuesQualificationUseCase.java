package com.EcoMentor_backend.EcoMentor.Address.useCases;


import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.GraphicDTOQualification;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import java.util.List;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@AllArgsConstructor
public class GetGraphValuesQualificationUseCase {
    private final AddressRepository addressRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public List<GraphicDTOQualification> execute(String town, String region, String province) {
        List<Address> addressList;

        if (town != null) {
            addressList = addressRepository.findByTown(town);
        } else if (region != null) {
            addressList = addressRepository.findByProvince(region);
        } else if (province != null) {
            addressList = addressRepository.findByProvince(province);
        } else {
            addressList = addressRepository.findAll();
        }
        int n = addressList.size();

        if (n == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No certificates found");
        }

        int numNonRenewable = 0;
        int numCo2 = 0;
        int numHeating = 0;
        int numRefrigeration = 0;
        int numAcs = 0;
        int numLighting = 0;

        for (Address address : addressList) {
            for (Certificate certificate : address.getCertificates()) {
                if (certificate.getCertificateType() == CertificateType.OFFICIAL) {
                    OfficialCertificate officialCertificate = (OfficialCertificate) certificate;

                    Integer nonRenewableValue = getNonRenewable(officialCertificate);
                    numNonRenewable += nonRenewableValue;


                    Integer co2Value = getCo2(officialCertificate);
                    numCo2 += co2Value;

                    Integer heatingValue = getHeating(officialCertificate);
                    numHeating += heatingValue;

                    Integer refrigerationValue = getRefrigeration(officialCertificate);
                    numRefrigeration += refrigerationValue;


                    Integer acsValue = getAcs(officialCertificate);
                    numAcs += acsValue;


                    Integer lightingValue = getLighting(officialCertificate);
                    numLighting += lightingValue;
                }
            }
        }
        GraphicDTOQualification nonRenewabl = createValue("nonRenewablePrimaryQualification", numNonRenewable, n);
        GraphicDTOQualification co2Graphic = createValue("co2Qualification", numCo2, n);
        GraphicDTOQualification heatingGraphic = createValue("heatingQualification", numHeating, n);
        GraphicDTOQualification refrigeration = createValue("refrigerationQualification", numRefrigeration, n);
        GraphicDTOQualification acsGraphic = createValue("acsQualification", numAcs, n);
        GraphicDTOQualification lightingGraphic = createValue("lightingQualification", numLighting, n);
        return List.of(nonRenewabl, co2Graphic, heatingGraphic, refrigeration, acsGraphic, lightingGraphic);

    }


    private GraphicDTOQualification createValue(String name, int value, int total) {
        return new GraphicDTOQualification(name, Qualification.fromValue(value / total));
    }

    private Integer getNonRenewable(OfficialCertificate certificate) {
        return certificate.getNonRenewablePrimaryQualification() != null
                ? certificate.getNonRenewablePrimaryQualification().getValue()
                : 6;
    }

    private Integer getCo2(OfficialCertificate certificate) {
        return certificate.getCo2Qualification() != null
                ? certificate.getCo2Qualification().getValue()
                : 6;
    }

    private Integer getHeating(OfficialCertificate certificate) {
        return certificate.getHeatingQualification() != null
                ? certificate.getHeatingQualification().getValue()
                : 6;
    }

    private Integer getRefrigeration(OfficialCertificate certificate) {
        return certificate.getRefrigerationQualification() != null
                ? certificate.getRefrigerationQualification().getValue()
                : 6;
    }

    private Integer getAcs(OfficialCertificate certificate) {
        return certificate.getAcsQualification() != null
                ? certificate.getAcsQualification().getValue()
                : 6;
    }

    private Integer getLighting(OfficialCertificate certificate) {
        return certificate.getLightingQualification() != null
                ? certificate.getLightingQualification().getValue()
                : 6;
    }

}