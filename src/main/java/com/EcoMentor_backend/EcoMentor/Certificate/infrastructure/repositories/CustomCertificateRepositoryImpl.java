package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.Year;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;



@Repository
public class CustomCertificateRepositoryImpl implements CustomCertificateRepository {

    private final EntityManager entityManager;

    public CustomCertificateRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Certificate> findCertificateByParameter(String parameter, Object value, double minLatitude,
                                                        double maxLatitude, double minLongitude,
                                                        double maxLongitude) {

        String jpql = "SELECT c FROM  Certificate c"
                + " JOIN c.address a"
                + " WHERE c." + parameter + " = :value "
                + "AND ST_Within(a.location, ST_MakeEnvelope(:minLongitude, :minLatitude, :maxLongitude,"
                + " :maxLatitude, 4326))";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("value", value);
        query.setParameter("minLatitude", minLatitude);
        query.setParameter("maxLatitude", maxLatitude);
        query.setParameter("minLongitude", minLongitude);
        query.setParameter("maxLongitude", maxLongitude);

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

    @Override
    public float calculateIndex1(float r, float io, float ir) {
        float c1;
        c1 = ((((r * io) / ir) - 1.0f) / (2 * (r - 1.0f))) + 0.6f;
        return c1;
    }

    @Override
    public float calculateIndex2(float rs, float io, float is) {
        float c2;
        c2 = ((((rs * io) / is) - 1.0f) / (2 * (rs - 1.0f))) + 0.5f;
        return c2;
    }

    @Override
    public Qualification findQualification(float c1, float c2) {
        if (c1 > 1.75f) {
            if (c2 >= 1.0f) {
                if (c2 < 1.5f) {
                    return Qualification.F;
                } else {
                    return Qualification.G;
                }
            } else {
                return Qualification.E;
            }
        } else {
            if (c1 < 0.5f) {
                if (c1 < 0.15f) {
                    return Qualification.A;
                } else {
                    return Qualification.B;
                }
            } else {
                if (c1 < 1.0f) {
                    return Qualification.C;
                } else {
                    return Qualification.D;
                }
            }
        }
    }

    @Override
    public Qualification findQualificationT(float c) {
        if (c >= 2.0f) {
            return Qualification.G;
        } else if (c >= 1.6f) {
            return Qualification.F;
        } else if (c >= 1.3f) {
            return Qualification.E;
        } else if (c >= 1.0f) {
            return Qualification.D;
        } else if (c >= 0.65f) {
            return Qualification.C;
        } else if (c >= 0.4f) {
            return Qualification.B;
        } else {
            return Qualification.A;
        }
    }

    @Override
    public float calculateBaseIoNPRE(int npREAprox, String buildingUse) {
        if (Objects.equals(buildingUse, "terciario")) {
            if (npREAprox == 0) {
                return 150.84f;
            } else if (npREAprox == 1) {
                return 208.84f;
            } else if (npREAprox == 2) {
                return 252.0f;
            } else if (npREAprox == 3) {
                return 285.0f;
            } else if (npREAprox == 4) {
                return 315.0f;
            } else if (npREAprox == 5) {
                return 358.0f;
            } else if (npREAprox == 6) {
                return 455.34f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
            if (npREAprox == 0) {
                return 36.21f;
            } else if (npREAprox == 1) {
                return 65.19f;
            } else if (npREAprox == 2) {
                return 97.86f;
            } else if (npREAprox == 3) {
                return 144.28f;
            } else if (npREAprox == 4) {
                return 231.02f;
            } else if (npREAprox == 5) {
                return 320.6f;
            } else if (npREAprox == 6) {
                return 408.65f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda bloque")) {
            if (npREAprox == 0) {
                return 36.21f;
            } else if (npREAprox == 1) {
                return 55.03f;
            } else if (npREAprox == 2) {
                return 79.55f;
            } else if (npREAprox == 3) {
                return 107.20f;
            } else if (npREAprox == 4) {
                return 179.0f;
            } else if (npREAprox == 5) {
                return 245.92f;
            } else if (npREAprox == 6) {
                return 315.0f;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Override
    public float calculateBaseIoHeating(int heatingAprox, String buildingUse) {
        if (Objects.equals(buildingUse, "terciario")) {
            if (heatingAprox == 0) {

            } else if (heatingAprox == 1) {
                return 9.9f;
            } else if (heatingAprox == 2) {
                return 19.6f;
            } else if (heatingAprox == 3) {
                return 27.08f;
            } else if (heatingAprox == 4) {
                return 29.47f;
            } else if (heatingAprox == 5) {
                return 31.81f;
            } else if (heatingAprox == 6) {
                return 35.15f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
            if (heatingAprox == 0) {
                return 8.62f;
            } else if (heatingAprox == 1) {
                return 13.72f;
            } else if (heatingAprox == 2) {
                return 18.85f;
            } else if (heatingAprox == 3) {
                return 26.16f;
            } else if (heatingAprox == 4) {
                return 42.73f;
            } else if (heatingAprox == 5) {
                return 64.46f;
            } else if (heatingAprox == 6) {
                return 87.82f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda bloque")) {
            if (heatingAprox == 0) {
                return 5.5f;
            } else if (heatingAprox == 1) {
                return 8.37f;
            } else if (heatingAprox == 2) {
                return 14.0f;
            } else if (heatingAprox == 3) {
                return 19.89f;
            } else if (heatingAprox == 4) {
                return 32.14f;
            } else if (heatingAprox == 5) {
                return 51.4f;
            } else if (heatingAprox == 6) {
                return 66.55f;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
        return 0f;
    }

    @Override
    public float calculateBaseIoRefrigeration(int refrigerationAprox, String buildingUse) {
        if (Objects.equals(buildingUse, "terciario")) {
            if (refrigerationAprox == 0) {
                return 2.54f;
            } else if (refrigerationAprox == 1) {
                return 4.51f;
            } else if (refrigerationAprox == 2) {
                return 5.91f;
            } else if (refrigerationAprox == 3) {
                return 7.4f;
            } else if (refrigerationAprox == 4) {
                return 8.88f;
            } else if (refrigerationAprox == 5) {
                return 10.25f;
            } else if (refrigerationAprox == 6) {
                return 11.97f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
            if (refrigerationAprox == 0) {
                return 1.79f;
            } else if (refrigerationAprox == 1) {
                return 2.65f;
            } else if (refrigerationAprox == 2) {
                return 3.49f;
            } else if (refrigerationAprox == 3) {
                return 4.66f;
            } else if (refrigerationAprox == 4) {
                return 6.04f;
            } else if (refrigerationAprox == 5) {
                return 6.76f;
            } else if (refrigerationAprox == 6) {
                return 7.86f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda bloque")) {
            if (refrigerationAprox == 0) {
                return 1.35f;
            } else if (refrigerationAprox == 1) {
                return 1.91f;
            } else if (refrigerationAprox == 2) {
                return 2.64f;
            } else if (refrigerationAprox == 3) {
                return 3.65f;
            } else if (refrigerationAprox == 4) {
                return 4.65f;
            } else if (refrigerationAprox == 5) {
                return 5.33f;
            } else if (refrigerationAprox == 6) {
                return 7.3f;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Override
    public float calculateBaseIoACS(int acsAprox, String buildingUse) {
        if (Objects.equals(buildingUse, "terciario")) {
            if (acsAprox == 0) {
                return 1.39f;
            } else if (acsAprox == 1) {
                return 3.5f;
            } else if (acsAprox == 2) {
                return 4.8f;
            } else if (acsAprox == 3) {
                return 5.04f;
            } else if (acsAprox == 4) {
                return 6.73f;
            } else if (acsAprox == 5) {
                return 12.09f;
            } else if (acsAprox == 6) {
                return 12.82f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
            if (acsAprox == 0) {
                return 2.57f;
            } else if (acsAprox == 1) {
                return 4.29f;
            } else if (acsAprox == 2) {
                return 4.97f;
            } else if (acsAprox == 3) {
                return 5.89f;
            } else if (acsAprox == 4) {
                return 6.7f;
            } else if (acsAprox == 5) {
                return 8.66f;
            } else if (acsAprox == 6) {
                return 14.44f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda bloque")) {
            if (acsAprox == 0) {
                return 2.25f;
            } else if (acsAprox == 1) {
                return 3.66f;
            } else if (acsAprox == 2) {
                return 4.17f;
            } else if (acsAprox == 3) {
                return 4.93f;
            } else if (acsAprox == 4) {
                return 5.77f;
            } else if (acsAprox == 5) {
                return 7.12f;
            } else if (acsAprox == 6) {
                return 14.22f;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Override
    public float calculateBaseIoLighting(int lightingAprox, String buildingUse) {
        if (Objects.equals(buildingUse, "terciario")) {
            if (lightingAprox == 0) {
                return 8.19f;
            } else if (lightingAprox == 1) {
                return 11.5f;
            } else if (lightingAprox == 2) {
                return 18.1f;
            } else if (lightingAprox == 3) {
                return 25.15f;
            } else if (lightingAprox == 4) {
                return 29.34f;
            } else if (lightingAprox == 5) {
                return 40.03f;
            } else if (lightingAprox == 6) {
                return 55.85f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
            if (lightingAprox == 0) {
                return 8.77f;
            } else if (lightingAprox == 1) {
                return 15.3f;
            } else if (lightingAprox == 2) {
                return 18.18f;
            } else if (lightingAprox == 3) {
                return 25.15f;
            } else if (lightingAprox == 4) {
                return 29.73f;
            } else if (lightingAprox == 5) {
                return 42.25f;
            } else if (lightingAprox == 6) {
                return 54.13f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda bloque")) {
            if (lightingAprox == 0) {
                return 8.92f;
            } else if (lightingAprox == 1) {
                return 15.3f;
            } else if (lightingAprox == 2) {
                return 18.18f;
            } else if (lightingAprox == 3) {
                return 25.15f;
            } else if (lightingAprox == 4) {
                return 29.73f;
            } else if (lightingAprox == 5) {
                return 42.25f;
            } else if (lightingAprox == 6) {
                return 54.13f;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

}
