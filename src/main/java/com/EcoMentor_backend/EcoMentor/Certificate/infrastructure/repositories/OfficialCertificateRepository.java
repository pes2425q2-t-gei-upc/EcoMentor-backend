package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficialCertificateRepository extends JpaRepository<OfficialCertificate, Long> {
    List<OfficialCertificate> findCertificateByClimateZone(String climaticZone);
    List<OfficialCertificate> findCertificateByNonRenewablePrimaryQualification(Qualification qualification);
    List<OfficialCertificate> findCertificateByCo2Qualification(Qualification qualification);
    List<OfficialCertificate> findCertificateByHeatingQualification(Qualification qualification);
    List<OfficialCertificate> findCertificateByRefrigerationQualification(Qualification qualification);
    List<OfficialCertificate> findCertificateByAcsQualification(Qualification qualification);
    List<OfficialCertificate> findCertificateByLightingQualification(Qualification qualification);
    List<OfficialCertificate> findCertificateByElectricVehicle(boolean electricVehicle);
    List<OfficialCertificate> findCertificateBySolarThermal(boolean solarThermal);
    List<OfficialCertificate> findCertificateByPhotovoltaicSolar(boolean photovoltaicSolar);
    List<OfficialCertificate> findCertificateByBiomass(boolean biomass);
    List<OfficialCertificate> findCertificateByDistrictNet(boolean districtNet);
    List<OfficialCertificate> findCertificateByGeothermal(boolean geothermal);
    List<OfficialCertificate> findCertificateByEnergeticRehabilitation(boolean energeticRehabilitation);

    OfficialCertificate findCertificatesByCertificateId(Long certificateId);

    boolean existsOfficialCertificateByDocumentId(String documentId);
}
