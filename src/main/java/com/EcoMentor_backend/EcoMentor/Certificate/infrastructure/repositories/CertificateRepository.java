package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate,String> {
    Certificate findCertificateByCertificateId(Long certificateId);
    List<Certificate> findCertificateByAddress(Long addressId);
    List<Certificate> findAll();
    List<Certificate> findCertificateByClimaticZone(String climaticZone);
    List<Certificate> findCertificateByNonRenewablePrimaryQualification(Qualification qualification);
    List<Certificate> findCertificateByCo2Qualification(Qualification qualification);
    List<Certificate> findCertificateByheatingQualification(Qualification qualification);
    List<Certificate> findCertificateByRefrigerationQualification(Qualification qualification);
    List<Certificate> findCertificateByACSQualification(Qualification qualification);
    List<Certificate> findCertificateBylightingQualification(Qualification qualification);
    List<Certificate> findCertificateByElectricVehicle(boolean electricVehicle);
    List<Certificate> findCertificateBySolarThermal(boolean solarThermal);
    List<Certificate> findCertificateByPhotovoltaicSolar(boolean photovoltaicSolar);
    List<Certificate> findCertificateByBiomass(boolean biomass);
    List<Certificate> findCertificateByDistrictNet(boolean districtNet);
    List<Certificate> findCertificateByGeothermal(boolean geothermal);
    List<Certificate> findCertificateByEnergeticRehabilitation(boolean energeticRehabilitation);
}
