package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import java.util.List;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculatorResultsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CertificateRepository extends JpaRepository<Certificate, String>, CustomCertificateRepository {

    Certificate findCertificateByCertificateId(Long certificateId);

    List<Certificate> findCertificateByAddressAddressId(Long addressId);

    List<Certificate> findAll();

    List<Certificate> findCertificateByParameter(String parameter, Object val, double minLatitude,
                                                 double maxLatitude, double minLongitude,
                                                 double maxLongitude);

    List<Certificate> findCertificateBySetOfValues(String parameter, List<Object> values);

    List<Certificate> findCertificateByMinMaxRange(String parameter, Object min, Object max);

    Object convertToCorrectType(String parameter, String value);

    float calculateIndex1(float r, float io, float ir);

    float calculateIndex2(float rs, float io, float is);

    Qualification findQualification(float c1, float c2);

    Qualification findQualificationT(float c);

    float calculateBaseIoNPRE(int npREAprox, String buildingUse);

    float calculateBaseIoHeating(int heatingAprox, String buildingUse);

    float calculateBaseIoRefrigeration(int refrigerationAprox, String buildingUse);

    float calculateBaseIoACS(int acsAprox, String buildingUse);

    float calculateBaseIoLighting(int lightingAprox, String buildingUse);

    CalculatorResultsDTO calculateQualifications(String climateZone, String buildingUse,
                                                 int nonRenewablePrimaryEnergyAprox,
                                                 boolean solarThermal, boolean photovoltaicSolar, boolean biomass,
                                                 boolean districtNet, boolean geothermal, float insulation,
                                                 float windowEfficiency, float heatingEmissionsInitial,
                                                 float refrigerationEmissionsInitial, float acsEmissionsInitial,
                                                 float lightingEmissionsInitial, float residentialUseVentilation);

}
