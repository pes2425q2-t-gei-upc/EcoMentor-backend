package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.Year;
import java.util.List;
import org.springframework.stereotype.Repository;



@Repository
public class CustomCertificateRepositoryImpl implements CustomCertificateRepository {

    private final EntityManager entityManager;

    public CustomCertificateRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Certificate> findCertificateByParameter(String parameter, Object value) {
        String jpql = "SELECT c FROM Certificate c WHERE c." + parameter + " = :value";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("value", value);
        return query.getResultList();
    }

    @Override
    public List<Certificate> findCertificateBySetOfValues(String parameter, List<Object> values) {
        String jpql = "SELECT c FROM Certificate c WHERE c." + parameter + " IN :values";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("values", values);
        return query.getResultList();
    }

    @Override
    public List<Certificate> findCertificateByMinMaxRange(String parameter, Object min, Object max) {
        String jpql = "SELECT c FROM Certificate c WHERE c." + parameter + " BETWEEN :min AND :max";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("min", min);
        query.setParameter("max", max);
        return query.getResultList();
    }

    @Override
    public Object convertToCorrectType(String parameter, String value) {
        switch (parameter) {
            case "certificateId", "address":
                return Long.parseLong(value);
            case "certificateType", "floor", "door", "climateZone", "documentId", "buildingUse":
                return value;
            case "CadastreMeters":
                return Integer.parseInt(value);
            case "buildingYear":
                return Year.parse(value);
            case "nonRenewablePrimaryQualification", "co2Qualification", "lightingQualification",
                 "heatingQualification", "acsQualification", "refrigerationQualification":
                return Qualification.valueOf(value);
            case "electricVehicle", "solarThermal", "photovoltaicSolar", "biomass", "districtNet",
                 "geothermal", "energeticRehabilitation":
                return Boolean.parseBoolean(value);
            case "nonRenewablePrimaryEnergy", "co2Emissions", "finalEnergyConsumption", "annualCost",
                 "insulation", "windowEfficiency", "heatingEmissions", "refrigerationEmissions",
                    "lightingEmissions", "acsEmissions", "residentialUseVentilation":
                return Float.parseFloat(value);
            case "entryDate":
                return java.sql.Date.valueOf(value);
            default:
                return null;
        }
    }

}
