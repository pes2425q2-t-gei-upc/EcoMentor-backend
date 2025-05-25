package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.KindOfHeating;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.KindOfRefrigeration;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculatorResultsDTO;
import java.util.List;
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

    CalculatorResultsDTO calculateQualificationsForANewCertificate(String climateZone, String buildingUse,
                                                                   boolean solarThermal, boolean photovoltaicSolar,
                                                                   float insulation, float windowEfficiency,
                                                                   float residentialUseVentilation,
                                                                   KindOfHeating typeOfHeating, KindOfHeating typeOfAcs,
                                                                   KindOfRefrigeration typeOfRefrigeration,
                                                                   float heatingConsumption, float acsConsumption,
                                                                   float refrigerationConsumption,
                                                                   float lightingConsumption, float cadastreMeters);

    CalculatorResultsDTO calculateRecomendationQualifications(String climateZone, String buildingUse,
                                                                     float nonRenewablePrimaryEnergyInitial,
                                                                     boolean solarThermal, boolean photovoltaicSolar,
                                                                     boolean biomass,
                                                                     boolean districtNet, boolean geothermal,
                                                                     float insulation,
                                                                     float windowEfficiency,
                                                                     float heatingEmissionsInitial,
                                                                     float refrigerationEmissionsInitial,
                                                                     float acsEmissionsInitial,
                                                                     float lightingEmissionsInitial,
                                                                     float residentialUseVentilation);

    CalculatorResultsDTO calculateQualifications(String climateZone, String buildingUse,
                                                 float ioNonRenewablePrimaryEnergy,
                                                 float ioCO2E, float ioHeating, float ioRefrigeration,
                                                 float ioACS, float ioLighting);

    float calculateAproxInsulation(int insulation, String buildingUse);

    float calculateAproxWindowEfficiciency(int windowEfficiency, String buildingUse);

    float calculateAproxResidentialUseVentilation(int residentialUseVentilation, String buildingUse);
}
