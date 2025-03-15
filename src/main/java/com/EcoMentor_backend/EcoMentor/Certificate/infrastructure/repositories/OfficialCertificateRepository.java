package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficialCertificateRepository extends JpaRepository<OfficialCertificate, Long> {
    List<OfficialCertificate> findCertificateByclimaticZone(String climaticZone);
    List<OfficialCertificate> findCertificateBynonRenewablePrimaryQualification(Qualification qualification);
    List<OfficialCertificate> findCertificateByco2Qualification(Qualification qualification);
    List<OfficialCertificate> findCertificateByheatingQualification(Qualification qualification);
    List<OfficialCertificate> findCertificateByrefrigerationQualification(Qualification qualification);
    List<OfficialCertificate> findCertificateByacsQualification(Qualification qualification);
    List<OfficialCertificate> findCertificateBylightingQualification(Qualification qualification);
    List<OfficialCertificate> findCertificateByelectricVehicle(boolean electricVehicle);
    List<OfficialCertificate> findCertificateBysolarThermal(boolean solarThermal);
    List<OfficialCertificate> findCertificateByphotovoltaicSolar(boolean photovoltaicSolar);
    List<OfficialCertificate> findCertificateBybiomass(boolean biomass);
    List<OfficialCertificate> findCertificateBydistrictNet(boolean districtNet);
    List<OfficialCertificate> findCertificateBygeothermal(boolean geothermal);
    List<OfficialCertificate> findCertificateByenergeticRehabilitation(boolean energeticRehabilitation);
}
