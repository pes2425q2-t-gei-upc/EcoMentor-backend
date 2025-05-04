package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculatorResultsDTO;
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

    @Override
    public float calculateAproxInsulation(int insulation, String buildingUse) {
        if (Objects.equals(buildingUse, "terciario")) {
            if (insulation == 0) {
                return 0.4215064102564102f;
            } else if (insulation == 1) {
                return 0.8229470198675497f;
            } else if (insulation == 2) {
                return 1.5809164969450102f;
            } else if (insulation == 3) {
                return 1.7843442622950825f;
            } else if (insulation == 4) {
                return 1.9593513513513514f;
            } else if (insulation == 5) {
                return 2.3513184079601985f;
            } else if (insulation == 6) {
                return 2.929905213270142f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
            if (insulation == 0) {
                return 0.4463405017921147f;
            } else if (insulation == 1) {
                return 0.6901919122686772f;
            } else if (insulation == 2) {
                return 1.1734839226685365f;
            } else if (insulation == 3) {
                return 1.685810271420969f;
            } else if (insulation == 4) {
                return 1.7867968851395204f;
            } else if (insulation == 5) {
                return 2.230012083303717f;
            } else if (insulation == 6) {
                return 2.864451894066376f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda bloque")) {
            if (insulation == 0) {
                return 0.5459663791391824f;
            } else if (insulation == 1) {
                return 1.050653140571582f;
            } else if (insulation == 2) {
                return 1.6791956149942528f;
            } else if (insulation == 3) {
                return 1.78320545104429f;
            } else if (insulation == 4) {
                return 1.937239989250202f;
            } else if (insulation == 5) {
                return 2.305815774131756f;
            } else if (insulation == 6) {
                return 2.830711557788945f;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Override
    public float calculateAproxWindowEfficiciency(int windowEfficiency, String buildingUse) {
        if (Objects.equals(buildingUse, "terciario")) {
            if (windowEfficiency == 0) {
                return 2.350766871165644f;
            } else if (windowEfficiency == 1) {
                return 3.392629757785467f;
            } else if (windowEfficiency == 2) {
                return 3.7594155844155845f;
            } else if (windowEfficiency == 3) {
                return 4.231644736842106f;
            } else if (windowEfficiency == 4) {
                return 5.073227848101266f;
            } else if (windowEfficiency == 5) {
                return 5.6646815834767645f;
            } else if (windowEfficiency == 6) {
                return 7.391304757f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
            if (windowEfficiency == 0) {
                return 2.725277699728388f;
            } else if (windowEfficiency == 1) {
                return 3.3582833494571642f;
            } else if (windowEfficiency == 2) {
                return 3.714480885048442f;
            } else if (windowEfficiency == 3) {
                return 3.8819905417024936f;
            } else if (windowEfficiency == 4) {
                return 4.201142256482131f;
            } else if (windowEfficiency == 5) {
                return 4.839439252336449f;
            } else if (windowEfficiency == 6) {
                return 5.8236826004369678f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda bloque")) {
            if (windowEfficiency == 0) {
                return 3.11522583231521f;
            } else if (windowEfficiency == 1) {
                return 3.729452989688357f;
            } else if (windowEfficiency == 2) {
                return 4.0529504940662395f;
            } else if (windowEfficiency == 3) {
                return 4.7941776501359055f;
            } else if (windowEfficiency == 4) {
                return 5.25666454183267f;
            } else if (windowEfficiency == 5) {
                return 5.89720154167835f;
            } else if (windowEfficiency == 6) {
                return 6.934f;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Override
    public float calculateAproxResidentialUseVentilation(int residentialUseVentilation, String buildingUse) {
        if (Objects.equals(buildingUse, "terciario")) {
            if (residentialUseVentilation == 0) {
                return 0.2826298701298702f;
            } else if (residentialUseVentilation == 1) {
                return 0.6188888888888888f;
            } else if (residentialUseVentilation == 2) {
                return 0.7964667535853975f;
            } else if (residentialUseVentilation == 3) {
                return 0.8381621621621622f;
            } else if (residentialUseVentilation == 4) {
                return 0.9429245283018869f;
            } else if (residentialUseVentilation == 5 || residentialUseVentilation == 6) {
                return 1.7203802281368819f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
            if (residentialUseVentilation == 0) {
                return 0.6208197351409903f;
            } else if (residentialUseVentilation == 1) {
                return 0.7447099337748346f;
            } else if (residentialUseVentilation == 2) {
                return 0.9307425343018563f;
            } else if (residentialUseVentilation == 3) {
                return 1.32672f;
            } else if (residentialUseVentilation == 4) {
                return 1.5643f;
            } else if (residentialUseVentilation == 5 || residentialUseVentilation == 6) {
                return 1.7682f;
            } else {
                return 0;
            }
        } else if (Objects.equals(buildingUse, "vivienda bloque")) {
            if (residentialUseVentilation == 0) {
                return 0.6256941877182173f;
            } else if (residentialUseVentilation == 1) {
                return 0.7525769139606542f;
            } else if (residentialUseVentilation == 2) {
                return 0.9331371529067893f;
            } else if (residentialUseVentilation == 3) {
                return 1.2354f;
            } else if (residentialUseVentilation == 4) {
                return 1.4567f;
            } else if (residentialUseVentilation == 5 || residentialUseVentilation == 6) {
                return 1.8764f;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Override
    public CalculatorResultsDTO calculateQualifications(String climateZone, String buildingUse,
                                                        int nonRenewablePrimaryEnergyAprox,
                                                        boolean solarThermal, boolean photovoltaicSolar,
                                                        boolean biomass,
                                                        boolean districtNet, boolean geothermal, float insulation,
                                                        float windowEfficiency, float heatingEmissionsInitial,
                                                        float refrigerationEmissionsInitial, float acsEmissionsInitial,
                                                        float lightingEmissionsInitial,
                                                        float residentialUseVentilation) {
        float indicadorPhotovoltaicSolarRefrigeration = 1.0f;
        float indicadorPhotovoltaicSolarNRPE = 1.0f;
        float indicadorPhotovoltaicSolarHeating = 1.0f;
        float indicadorPhotovoltaicSolarACS = 1.0f;
        float indicadorPhotovoltaicSolarLighting = 1.0f;
        if (photovoltaicSolar) {
            indicadorPhotovoltaicSolarRefrigeration = indicadorPhotovoltaicSolarRefrigeration - 0.18f;
            indicadorPhotovoltaicSolarNRPE = indicadorPhotovoltaicSolarNRPE - 0.20f;
            indicadorPhotovoltaicSolarHeating = indicadorPhotovoltaicSolarHeating - 0.18f;
            indicadorPhotovoltaicSolarACS = indicadorPhotovoltaicSolarACS - 0.18f;
            indicadorPhotovoltaicSolarLighting = indicadorPhotovoltaicSolarLighting - 0.18f;
        }

        float indicadorSolarThermalNRPE = 1.0f;
        float indicadorSolarThermalHeating = 1.0f;
        float indicadorSolarThermalACS = 1.0f;
        if (solarThermal) {
            indicadorSolarThermalNRPE = indicadorSolarThermalNRPE - 0.25f;
            indicadorSolarThermalHeating = indicadorSolarThermalHeating - 0.32f;
            indicadorSolarThermalACS = indicadorSolarThermalACS - 0.60f;
            if (photovoltaicSolar) {
                indicadorPhotovoltaicSolarNRPE = indicadorPhotovoltaicSolarNRPE + 0.04f;
                indicadorPhotovoltaicSolarHeating = 1.0f;
                indicadorPhotovoltaicSolarACS = 1.0f;
            }
        }

        float indicadorBiomassNRPE = 1.0f;
        float indicadorBiomassHeating = 1.0f;
        float indicadorBiomassACS = 1.0f;
        if (biomass) {
            indicadorBiomassNRPE = indicadorBiomassNRPE - 0.784f;
            indicadorBiomassHeating = indicadorBiomassHeating - 0.784f;
            indicadorBiomassACS = indicadorBiomassACS - 0.784f;
            if (photovoltaicSolar) {
                indicadorPhotovoltaicSolarNRPE = indicadorPhotovoltaicSolarNRPE + 0.04f;
                indicadorPhotovoltaicSolarHeating = 1.0f;
                indicadorPhotovoltaicSolarACS = 1.0f;
            }
            if (solarThermal) {
                indicadorSolarThermalNRPE = 0.85f;
                indicadorSolarThermalHeating = 0.83f;
                indicadorSolarThermalACS = 0.85f;
            }
        }


        float indicadorDistrictNetRefrigeration = 1.0f;
        float indicadorDistrictNetNRPE = 1.0f;
        float indicadorDistrictNetHeating = 1.0f;
        float indicadorDistrictNetACS = 1.0f;
        if (districtNet) {
            indicadorDistrictNetRefrigeration = indicadorDistrictNetRefrigeration - 0.22f;
            indicadorDistrictNetNRPE = indicadorDistrictNetNRPE - 0.5f;
            indicadorDistrictNetHeating = indicadorDistrictNetHeating - 0.5f;
            indicadorDistrictNetACS = indicadorDistrictNetACS - 0.5f;
            if (photovoltaicSolar) {
                indicadorPhotovoltaicSolarNRPE = indicadorPhotovoltaicSolarNRPE + 0.04f;
                indicadorPhotovoltaicSolarHeating = 1.0f;
                indicadorPhotovoltaicSolarACS = 1.0f;
            }
            if (solarThermal) {
                indicadorSolarThermalNRPE = 0.85f;
                indicadorSolarThermalHeating = 0.83f;
                indicadorSolarThermalACS = 0.85f;
            }
            if (biomass) {
                indicadorBiomassNRPE = 0.984f;
                indicadorBiomassHeating = 0.984f;
                indicadorBiomassACS = 0.984f;
            }
        }


        float indicadorGeothermalRefrigeration = 1.0f;
        float indicadorGeothermalNRPE = 1.0f;
        float indicadorGeothermalHeating = 1.0f;
        float indicadorGeothermalACS = 1.0f;
        if (geothermal) {
            indicadorGeothermalRefrigeration = indicadorGeothermalRefrigeration - 0.37f;
            indicadorGeothermalNRPE = indicadorGeothermalNRPE - 0.35f;
            indicadorGeothermalHeating = indicadorGeothermalHeating - 0.38f;
            indicadorGeothermalACS = indicadorGeothermalACS - 0.38f;
            if (photovoltaicSolar) {
                indicadorPhotovoltaicSolarNRPE = indicadorPhotovoltaicSolarNRPE + 0.04f;
                indicadorPhotovoltaicSolarHeating = 1.0f;
                indicadorPhotovoltaicSolarACS = 1.0f;
            }
            if (solarThermal) {
                indicadorSolarThermalNRPE = 0.85f;
                indicadorSolarThermalHeating = 0.83f;
                indicadorSolarThermalACS = 0.85f;
            }
            if (biomass) {
                indicadorGeothermalNRPE = 0.95f;
                indicadorGeothermalHeating = 0.92f;
                indicadorGeothermalACS = 0.92f;
            }
            if (districtNet) {
                indicadorDistrictNetRefrigeration = 0.98f;
                indicadorGeothermalNRPE = 0.95f;
                indicadorGeothermalHeating = 0.92f;
                indicadorGeothermalACS = 0.92f;
            }
        }


        float nonRenewablePrimaryEnergyInitial = calculateBaseIoNPRE(nonRenewablePrimaryEnergyAprox, buildingUse);

        float ioNonRenewablePrimaryEnergy = nonRenewablePrimaryEnergyInitial * indicadorBiomassNRPE
                * indicadorGeothermalNRPE * indicadorDistrictNetNRPE * indicadorPhotovoltaicSolarNRPE
                * indicadorSolarThermalNRPE;
        ioNonRenewablePrimaryEnergy += 19.991f * (insulation - 0.779f) + 0.001f * (windowEfficiency - 1.6f)
                + 48.362f * (residentialUseVentilation - 0.612f);


        float ioHeating = heatingEmissionsInitial * indicadorBiomassHeating
                * indicadorGeothermalHeating * indicadorDistrictNetHeating * indicadorPhotovoltaicSolarHeating
                * indicadorSolarThermalHeating;
        ioHeating +=  4.209f * (insulation - 0.779f) + 0.001f * (windowEfficiency - 1.6f)
                + 5.984f * (residentialUseVentilation - 0.612f);


        float ioRefrigeration = refrigerationEmissionsInitial
                * indicadorGeothermalRefrigeration * indicadorDistrictNetRefrigeration
                * indicadorPhotovoltaicSolarRefrigeration;
        ioRefrigeration += 0.039f * (insulation - 0.779f) + 0.001f * (windowEfficiency - 1.6f)
                + 0.432f * (residentialUseVentilation - 0.612f);


        float ioACS = acsEmissionsInitial * indicadorBiomassACS * indicadorGeothermalACS
                * indicadorDistrictNetACS * indicadorPhotovoltaicSolarACS * indicadorSolarThermalACS;


        float ioLighting = lightingEmissionsInitial * indicadorPhotovoltaicSolarLighting;
        float ioCO2E = ioHeating + ioRefrigeration + ioACS + ioLighting;


        if (Objects.equals(buildingUse, "terciario")) {
            float irNonRenewablePrimaryEnergy = 0.0f;
            float irCO2Em = 0.0f;
            float irHeating = 0.0f;
            float irRefrigeration = 0.0f;
            float irACS = 0.0f;
            float irLighting = 0.0f;
            if (Objects.equals(climateZone, "B3")) {
                irNonRenewablePrimaryEnergy = 190.298671875f;
                irCO2Em = 37.08639686684073f;
                irHeating = 14.476484149855908f;
                irRefrigeration = 5.590584795321638f;
                irACS = 7.262191780821918f;
                irLighting = 9.976079136690647f;
            } else if (Objects.equals(climateZone, "C2")) {
                irNonRenewablePrimaryEnergy = 185.54200631911533f;
                irCO2Em = 35.02690449311357f;
                irHeating = 14.425793728564429f;
                irRefrigeration = 4.194303664921466f;
                irACS = 7.089139447236181f;
                irLighting = 10.476953966699314f;
            } else if (Objects.equals(climateZone, "C3")) {
                irNonRenewablePrimaryEnergy = 204.91268571428571f;
                irCO2Em = 100.75619999999999f;
                irHeating = 16.66694533762058f;
                irRefrigeration = 5.721611842105264f;
                irACS = 6.312765151515151f;
                irLighting = 12.014090909090909f;
            } else if (Objects.equals(climateZone, "D1")) {
                irNonRenewablePrimaryEnergy = 174.1260512820513f;
                irCO2Em = 33.80123076923077f;
                irHeating = 19.497386363636362f;
                irRefrigeration = 2.522972972972973f;
                irACS = 5.336214285714285f;
                irLighting = 8.449925925925927f;
            } else if (Objects.equals(climateZone, "D2")) {
                irNonRenewablePrimaryEnergy = 191.68404634581105f;
                irCO2Em = 36.0336898395722f;
                irHeating = 18.05002f;
                irRefrigeration = 3.619591397849462f;
                irACS = 6.900191846522782f;
                irLighting = 9.854258760107816f;
            } else if (Objects.equals(climateZone, "D3")) {
                irNonRenewablePrimaryEnergy = 218.80890489913546f;
                irCO2Em = 41.763746397694526f;
                irHeating = 22.372208201892743f;
                irRefrigeration = 4.83f;
                irACS = 6.583858267716535f;
                irLighting = 10.811825396825396f;
            } else if (Objects.equals(climateZone, "E1")) {
                irNonRenewablePrimaryEnergy = 237.6812987012987f;
                irCO2Em = 46.83206451612904f;
                irHeating = 29.2544966442953f;
                irRefrigeration = 1.9139473684210526f;
                irACS = 7.76841726618705f;
                irLighting = 11.448981481481482f;
            }

            float cnRPE = ioNonRenewablePrimaryEnergy / irNonRenewablePrimaryEnergy;
            Qualification nonRenewablePrimaryQualification = findQualificationT(cnRPE);

            float co2E = ioCO2E / irCO2Em;
            Qualification co2Qualification = findQualificationT(co2E);

            float heatingE = ioHeating / irHeating;
            Qualification heatingQualification = findQualificationT(heatingE);

            float refrigerationE = ioRefrigeration / irRefrigeration;
            Qualification refrigerationQualification = findQualificationT(refrigerationE);

            float acsE = ioACS / irACS;
            Qualification acsQualification = findQualificationT(acsE);

            float lightingE = ioLighting / irLighting;
            Qualification lightingQualification = findQualificationT(lightingE);

            return new CalculatorResultsDTO(null, ioNonRenewablePrimaryEnergy, ioCO2E,
                    ioHeating, ioRefrigeration, ioACS, ioLighting, nonRenewablePrimaryQualification,
                    co2Qualification, heatingQualification,
                    refrigerationQualification, acsQualification, lightingQualification);


        } else {
            float rnRPE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    rnRPE = 1.6f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rnRPE = 1.5f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rnRPE = 1.55f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rnRPE = 1.45f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rnRPE = 1.5f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rnRPE = 1.5f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rnRPE = 1.45f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    rnRPE = 1.6f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rnRPE = 1.5f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rnRPE = 1.55f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rnRPE = 1.45f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rnRPE = 1.5f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rnRPE = 1.5f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rnRPE = 1.45f;
                }
            }
            float rcO2E = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    rcO2E = 1.6f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rcO2E = 1.5f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rcO2E = 1.55f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rcO2E = 1.45f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rcO2E = 1.5f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rcO2E = 1.5f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rcO2E = 1.45f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    rcO2E = 1.6f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rcO2E = 1.5f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rcO2E = 1.55f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rcO2E = 1.45f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rcO2E = 1.5f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rcO2E = 1.5f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rcO2E = 1.45f;
                }
            }
            float rhE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    rhE = 1.6f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rhE = 1.5f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rhE = 1.5f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rhE = 1.5f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rhE = 1.5f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rhE = 1.5f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rhE = 1.4f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    rhE = 1.7f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rhE = 1.7f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rhE = 1.7f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rhE = 1.7f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rhE = 1.7f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rhE = 1.7f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rhE = 1.7f;
                }
            }
            float rrE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    rrE = 1.4f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rrE = 1.5f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rrE = 1.4f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rrE = 17.18104495747266f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rrE = 1.5f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rrE = 1.4f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rrE = 17.098265073947665f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    rrE = 1.5f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rrE = 1.6f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rrE = 1.5f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rrE = 19.277490473598256f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rrE = 1.6f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rrE = 1.5f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rrE = 14.517805858701896f;
                }
            }
            float raCSE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    raCSE = 4.747910177216973f;
                } else if (Objects.equals(climateZone, "C2")) {
                    raCSE = 4.53561375862631f;
                } else if (Objects.equals(climateZone, "C3")) {
                    raCSE = 4.574286307259987f;
                } else if (Objects.equals(climateZone, "D1")) {
                    raCSE = 4.948320075361105f;
                } else if (Objects.equals(climateZone, "D2")) {
                    raCSE = 4.361113821138211f;
                } else if (Objects.equals(climateZone, "D3")) {
                    raCSE = 5.973510565374183f;
                } else if (Objects.equals(climateZone, "E1")) {
                    raCSE = 6.353116818792896f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    raCSE = 2.9051162111789193f;
                } else if (Objects.equals(climateZone, "C2")) {
                    raCSE = 3.5274376591482137f;
                } else if (Objects.equals(climateZone, "C3")) {
                    raCSE = 3.018863229029079f;
                } else if (Objects.equals(climateZone, "D1")) {
                    raCSE = 2.929780229340429f;
                } else if (Objects.equals(climateZone, "D2")) {
                    raCSE = 2.865663735457903f;
                } else if (Objects.equals(climateZone, "D3")) {
                    raCSE = 2.2510701748206587f;
                } else if (Objects.equals(climateZone, "E1")) {
                    raCSE = 2.771597996346492f;
                }
            }
            float rlE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    rlE = 1.8897637795275593f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rlE = 2.9839377848077424f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rlE = 1.88f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rlE = 1.250294048459186f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rlE = 2.8107427710985107f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rlE = 2.846490100282849f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rlE = 6.006211180124224f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    rlE = 4.057041991824601f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rlE = 3.193852187088404f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rlE = 2.167369901547117f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rlE = 1.84281179138322f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rlE = 3.1048374467552f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rlE = 6.634054079297623f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rlE = 2.4680603948896636f;
                }
            }

            //ir para NonRenewable Primary Energy
            float irNRPE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    irNRPE = 123.60802095459837f;
                } else if (Objects.equals(climateZone, "C2")) {
                    irNRPE = 107.99187744088617f;
                } else if (Objects.equals(climateZone, "C3")) {
                    irNRPE = 130.05985307908722f;
                } else if (Objects.equals(climateZone, "D1")) {
                    irNRPE = 110.3063795986622f;
                } else if (Objects.equals(climateZone, "D2")) {
                    irNRPE = 122.51527892951377f;
                } else if (Objects.equals(climateZone, "D3")) {
                    irNRPE = 133.1710768477729f;
                } else if (Objects.equals(climateZone, "E1")) {
                    irNRPE = 149.60565098841172f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    irNRPE = 144.2240018802883f;
                } else if (Objects.equals(climateZone, "C2")) {
                    irNRPE = 128.69871226937488f;
                } else if (Objects.equals(climateZone, "C3")) {
                    irNRPE = 155.13230090311987f;
                } else if (Objects.equals(climateZone, "D1")) {
                    irNRPE = 157.9198860182371f;
                } else if (Objects.equals(climateZone, "D2")) {
                    irNRPE = 159.74401045811072f;
                } else if (Objects.equals(climateZone, "D3")) {
                    irNRPE = 184.08121951219513f;
                } else if (Objects.equals(climateZone, "E1")) {
                    irNRPE = 189.40397254397257f;
                }
            }
            float irCO2E = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    irCO2E = 24.030250582750586f;
                } else if (Objects.equals(climateZone, "C2")) {
                    irCO2E = 21.93782466377286f;
                } else if (Objects.equals(climateZone, "C3")) {
                    irCO2E = 25.631471875f;
                } else if (Objects.equals(climateZone, "D1")) {
                    irCO2E = 23.644488255033558f;
                } else if (Objects.equals(climateZone, "D2")) {
                    irCO2E = 25.654519720702023f;
                } else if (Objects.equals(climateZone, "D3")) {
                    irCO2E = 27.435831289847968f;
                } else if (Objects.equals(climateZone, "E1")) {
                    irCO2E = 31.55025273224044f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    irCO2E = 32.88521391631406f;
                } else if (Objects.equals(climateZone, "C2")) {
                    irCO2E = 24.962196708944315f;
                } else if (Objects.equals(climateZone, "C3")) {
                    irCO2E = 29.053090349075973f;
                } else if (Objects.equals(climateZone, "D1")) {
                    irCO2E = 31.957512343334596f;
                } else if (Objects.equals(climateZone, "D2")) {
                    irCO2E = 31.633280645894928f;
                } else if (Objects.equals(climateZone, "D3")) {
                    irCO2E = 37.203303357314155f;
                } else if (Objects.equals(climateZone, "E1")) {
                    irCO2E = 38.26832260832261f;
                }
            }
            float irHE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    irHE = 10.7f;
                } else if (Objects.equals(climateZone, "C2")) {
                    irHE = 17.0f;
                } else if (Objects.equals(climateZone, "C3")) {
                    irHE = 17.0f;
                } else if (Objects.equals(climateZone, "D1")) {
                    irHE = 25.0f;
                } else if (Objects.equals(climateZone, "D2")) {
                    irHE = 25.0f;
                } else if (Objects.equals(climateZone, "D3")) {
                    irHE = 25.0f;
                } else if (Objects.equals(climateZone, "E1")) {
                    irHE = 33.1f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    irHE = 6.7f;
                } else if (Objects.equals(climateZone, "C2")) {
                    irHE = 11.3f;
                } else if (Objects.equals(climateZone, "C3")) {
                    irHE = 11.3f;
                } else if (Objects.equals(climateZone, "D1")) {
                    irHE = 17.0f;
                } else if (Objects.equals(climateZone, "D2")) {
                    irHE = 17.0f;
                } else if (Objects.equals(climateZone, "D3")) {
                    irHE = 17.0f;
                } else if (Objects.equals(climateZone, "E1")) {
                    irHE = 22.8f;
                }
            }
            float irRE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    irRE = 5.4f;
                } else if (Objects.equals(climateZone, "C2")) {
                    irRE = 2.7f;
                } else if (Objects.equals(climateZone, "C3")) {
                    irRE = 5.4f;
                } else if (Objects.equals(climateZone, "D1")) {
                    irRE = 0.8590522478736331f;
                } else if (Objects.equals(climateZone, "D2")) {
                    irRE = 2.7f;
                } else if (Objects.equals(climateZone, "D3")) {
                    irRE = 5.4f;
                } else if (Objects.equals(climateZone, "E1")) {
                    irRE = 1.8590522478736331f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    irRE = 3.7f;
                } else if (Objects.equals(climateZone, "C2")) {
                    irRE = 1.8f;
                } else if (Objects.equals(climateZone, "C3")) {
                    irRE = 3.7f;
                } else if (Objects.equals(climateZone, "D1")) {
                    irRE = 0.7710996189439303f;
                } else if (Objects.equals(climateZone, "D2")) {
                    irRE = 1.8f;
                } else if (Objects.equals(climateZone, "D3")) {
                    irRE = 3.7f;
                } else if (Objects.equals(climateZone, "E1")) {
                    irRE = 1.1614244686961517f;
                }
            }
            float iraCSE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    iraCSE = 2.76f;
                } else if (Objects.equals(climateZone, "C2")) {
                    iraCSE = 4.67f;
                } else if (Objects.equals(climateZone, "C3")) {
                    iraCSE = 2.68f;
                } else if (Objects.equals(climateZone, "D1")) {
                    iraCSE = 4.88f;
                } else if (Objects.equals(climateZone, "D2")) {
                    iraCSE = 3.75f;
                } else if (Objects.equals(climateZone, "D3")) {
                    iraCSE = 2.7f;
                } else if (Objects.equals(climateZone, "E1")) {
                    iraCSE = 3.73f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    iraCSE = 2.01f;
                } else if (Objects.equals(climateZone, "C2")) {
                    iraCSE = 3.43f;
                } else if (Objects.equals(climateZone, "C3")) {
                    iraCSE = 1.96f;
                } else if (Objects.equals(climateZone, "D1")) {
                    iraCSE = 3.57f;
                } else if (Objects.equals(climateZone, "D2")) {
                    iraCSE = 2.75f;
                } else if (Objects.equals(climateZone, "D3")) {
                    iraCSE = 1.98f;
                } else if (Objects.equals(climateZone, "E1")) {
                    iraCSE = 2.73f;
                }
            }
            float irLE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    irLE = 12.0f;
                } else if (Objects.equals(climateZone, "C2")) {
                    irLE = 17.939433962264147f;
                } else if (Objects.equals(climateZone, "C3")) {
                    irLE = 9.87f;
                } else if (Objects.equals(climateZone, "D1")) {
                    irLE = 10.63f;
                } else if (Objects.equals(climateZone, "D2")) {
                    irLE = 12.575263157894737f;
                } else if (Objects.equals(climateZone, "D3")) {
                    irLE = 11.07f;
                } else if (Objects.equals(climateZone, "E1")) {
                    irLE = 9.67f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    irLE = 14.24021739130435f;
                } else if (Objects.equals(climateZone, "C2")) {
                    irLE = 13.382240663900415f;
                } else if (Objects.equals(climateZone, "C3")) {
                    irLE = 7.705f;
                } else if (Objects.equals(climateZone, "D1")) {
                    irLE = 9.674761904761905f;
                } else if (Objects.equals(climateZone, "D2")) {
                    irLE = 15.25096153846154f;
                } else if (Objects.equals(climateZone, "D3")) {
                    irLE = 12.352608695652174f;
                } else if (Objects.equals(climateZone, "E1")) {
                    irLE = 10.625f;
                }
            }

            float rsNRPE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    rsNRPE = 1.6f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rsNRPE = 1.5f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rsNRPE = 1.55f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rsNRPE = 1.45f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rsNRPE = 1.5f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rsNRPE = 1.5f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rsNRPE = 1.45f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    rsNRPE = 1.6f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rsNRPE = 1.5f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rsNRPE = 1.55f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rsNRPE = 1.45f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rsNRPE = 1.5f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rsNRPE = 1.5f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rsNRPE = 1.45f;
                }
            }
            float rsCO2E = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    rsCO2E = 1.6f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rsCO2E = 1.5f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rsCO2E = 1.55f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rsCO2E = 1.45f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rsCO2E = 1.5f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rsCO2E = 1.5f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rsCO2E = 1.45f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    rsCO2E = 1.6f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rsCO2E = 1.5f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rsCO2E = 1.55f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rsCO2E = 1.45f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rsCO2E = 1.5f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rsCO2E = 1.5f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rsCO2E = 1.45f;
                }
            }
            float rsHE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    rsHE = 1.6f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rsHE = 1.5f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rsHE = 1.5f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rsHE = 1.5f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rsHE = 1.5f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rsHE = 1.5f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rsHE = 1.4f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    rsHE = 1.7f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rsHE = 1.7f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rsHE = 1.7f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rsHE = 1.7f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rsHE = 1.7f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rsHE = 1.7f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rsHE = 1.7f;
                }
            }
            float rsRE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    rsRE = 1.4f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rsRE = 1.5f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rsRE = 1.4f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rsRE = 29.47811976549414f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rsRE = 1.5f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rsRE = 1.4f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rsRE = 33.212399913438645f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    rsRE = 1.5f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rsRE = 1.6f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rsRE = 1.5f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rsRE = 34.62791356044163f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rsRE = 1.6f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rsRE = 1.5f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rsRE = 48.13608673205988f;
                }
            }
            float rsaCSE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    rsaCSE = 2.76f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rsaCSE = 4.67f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rsaCSE = 2.68f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rsaCSE = 4.88f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rsaCSE = 3.75f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rsaCSE = 2.7f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rsaCSE = 3.73f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    rsaCSE = 2.01f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rsaCSE = 3.43f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rsaCSE = 1.96f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rsaCSE = 3.57f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rsaCSE = 2.75f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rsaCSE = 1.98f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rsaCSE = 2.73f;
                }
            }
            float rsLE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    rsLE = 4.913519023610767f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rsLE = 4.077138306573113f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rsLE = 4.316018306636155f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rsLE = 3.101887334738455f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rsLE = 3.729835523953171f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rsLE = 2.911989179789392f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rsLE = 3.3712955546655996f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    rsLE = 4.747188413162242f;
                } else if (Objects.equals(climateZone, "C2")) {
                    rsLE = 4.236709940654199f;
                } else if (Objects.equals(climateZone, "C3")) {
                    rsLE = 3.7504766036892407f;
                } else if (Objects.equals(climateZone, "D1")) {
                    rsLE = 2.968550799741844f;
                } else if (Objects.equals(climateZone, "D2")) {
                    rsLE = 4.028547538651784f;
                } else if (Objects.equals(climateZone, "D3")) {
                    rsLE = 3.96227548747857f;
                } else if (Objects.equals(climateZone, "E1")) {
                    rsLE = 4.028361344537815f;
                }
            }


            float isNRPE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    isNRPE = 216.40963269651624f;
                } else if (Objects.equals(climateZone, "C2")) {
                    isNRPE = 235.6803176572668f;
                } else if (Objects.equals(climateZone, "C3")) {
                    isNRPE = 232.74149198627464f;
                } else if (Objects.equals(climateZone, "D1")) {
                    isNRPE = 259.65423648574256f;
                } else if (Objects.equals(climateZone, "D2")) {
                    isNRPE = 260.7511923983353f;
                } else if (Objects.equals(climateZone, "D3")) {
                    isNRPE = 291.4008411443475f;
                } else if (Objects.equals(climateZone, "E1")) {
                    isNRPE = 308.6080620490621f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    isNRPE = 189.77545205119358f;
                } else if (Objects.equals(climateZone, "C2")) {
                    isNRPE = 197.82362432257418f;
                } else if (Objects.equals(climateZone, "C3")) {
                    isNRPE = 205.22495816511613f;
                } else if (Objects.equals(climateZone, "D1")) {
                    isNRPE = 238.72735199004978f;
                } else if (Objects.equals(climateZone, "D2")) {
                    isNRPE = 236.46605935916486f;
                } else if (Objects.equals(climateZone, "D3")) {
                    isNRPE = 265.3957768508863f;
                } else if (Objects.equals(climateZone, "E1")) {
                    isNRPE = 287.14343223339466f;
                }
            }
            float isCO2E = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    isCO2E = 44.24119136350192f;
                } else if (Objects.equals(climateZone, "C2")) {
                    isCO2E = 51.152608733381925f;
                } else if (Objects.equals(climateZone, "C3")) {
                    isCO2E = 52.41476633078527f;
                } else if (Objects.equals(climateZone, "D1")) {
                    isCO2E = 59.86725220264317f;
                } else if (Objects.equals(climateZone, "D2")) {
                    isCO2E = 57.66598360450249f;
                } else if (Objects.equals(climateZone, "D3")) {
                    isCO2E = 64.06385844748858f;
                } else if (Objects.equals(climateZone, "E1")) {
                    isCO2E = 67.1499985567903f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    isCO2E = 36.83232216198456f;
                } else if (Objects.equals(climateZone, "C2")) {
                    isCO2E = 40.49728998520972f;
                } else if (Objects.equals(climateZone, "C3")) {
                    isCO2E = 40.4949760107659f;
                } else if (Objects.equals(climateZone, "D1")) {
                    isCO2E = 50.14365677676183f;
                } else if (Objects.equals(climateZone, "D2")) {
                    isCO2E = 50.075170968663684f;
                } else if (Objects.equals(climateZone, "D3")) {
                    isCO2E = 54.95902034984747f;
                } else if (Objects.equals(climateZone, "E1")) {
                    isCO2E = 59.149276306472174f;
                }
            }
            float isHE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    isHE = 39.27f;
                } else if (Objects.equals(climateZone, "C2")) {
                    isHE = 51.53f;
                } else if (Objects.equals(climateZone, "C3")) {
                    isHE = 51.53f;
                } else if (Objects.equals(climateZone, "D1")) {
                    isHE = 67.77f;
                } else if (Objects.equals(climateZone, "D2")) {
                    isHE = 67.77f;
                } else if (Objects.equals(climateZone, "D3")) {
                    isHE = 67.77f;
                } else if (Objects.equals(climateZone, "E1")) {
                    isHE = 95.18f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    isHE = 30.22f;
                } else if (Objects.equals(climateZone, "C2")) {
                    isHE = 40.91f;
                } else if (Objects.equals(climateZone, "C3")) {
                    isHE = 40.91f;
                } else if (Objects.equals(climateZone, "D1")) {
                    isHE = 54.77f;
                } else if (Objects.equals(climateZone, "D2")) {
                    isHE = 54.77f;
                } else if (Objects.equals(climateZone, "D3")) {
                    isHE = 54.77f;
                } else if (Objects.equals(climateZone, "E1")) {
                    isHE = 77.68f;
                }
            }
            float isRE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    isRE = 9.17f;
                } else if (Objects.equals(climateZone, "C2")) {
                    isRE = 4.58f;
                } else if (Objects.equals(climateZone, "C3")) {
                    isRE = 9.17f;
                } else if (Objects.equals(climateZone, "D1")) {
                    isRE = 0.8843435929648241f;
                } else if (Objects.equals(climateZone, "D2")) {
                    isRE = 4.58f;
                } else if (Objects.equals(climateZone, "D3")) {
                    isRE = 9.17f;
                } else if (Objects.equals(climateZone, "E1")) {
                    isRE = 1.3284959965375458f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    isRE = 6.58f;
                } else if (Objects.equals(climateZone, "C2")) {
                    isRE = 3.19f;
                } else if (Objects.equals(climateZone, "C3")) {
                    isRE = 6.58f;
                } else if (Objects.equals(climateZone, "D1")) {
                    isRE = 0.6925582712088326f;
                } else if (Objects.equals(climateZone, "D2")) {
                    isRE = 3.19f;
                } else if (Objects.equals(climateZone, "D3")) {
                    isRE = 6.58f;
                } else if (Objects.equals(climateZone, "E1")) {
                    isRE = 0.9627217346411976f;
                }
            }
            float isaCSE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    isaCSE = 6.48f;
                } else if (Objects.equals(climateZone, "C2")) {
                    isaCSE = 6.67f;
                } else if (Objects.equals(climateZone, "C3")) {
                    isaCSE = 6.69f;
                } else if (Objects.equals(climateZone, "D1")) {
                    isaCSE = 6.97f;
                } else if (Objects.equals(climateZone, "D2")) {
                    isaCSE = 6.89f;
                } else if (Objects.equals(climateZone, "D3")) {
                    isaCSE = 6.75f;
                } else if (Objects.equals(climateZone, "E1")) {
                    isaCSE = 7.11f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    isaCSE = 4.73f;
                } else if (Objects.equals(climateZone, "C2")) {
                    isaCSE = 4.9f;
                } else if (Objects.equals(climateZone, "C3")) {
                    isaCSE = 4.9f;
                } else if (Objects.equals(climateZone, "D1")) {
                    isaCSE = 5.1f;
                } else if (Objects.equals(climateZone, "D2")) {
                    isaCSE = 5.05f;
                } else if (Objects.equals(climateZone, "D3")) {
                    isaCSE = 4.95f;
                } else if (Objects.equals(climateZone, "E1")) {
                    isaCSE = 5.21f;
                }
            }
            float isLE = 0.0f;
            if (Objects.equals(buildingUse, "vivienda unifamiliar")) {
                if (Objects.equals(climateZone, "B3")) {
                    isLE = 16.067207207207208f;
                } else if (Objects.equals(climateZone, "C2")) {
                    isLE = 14.571692307692308f;
                } else if (Objects.equals(climateZone, "C3")) {
                    isLE = 16.40086956521739f;
                } else if (Objects.equals(climateZone, "D1")) {
                    isLE = 16.18875f;
                } else if (Objects.equals(climateZone, "D2")) {
                    isLE = 14.098778280542986f;
                } else if (Objects.equals(climateZone, "D3")) {
                    isLE = 13.70090909090909f;
                } else if (Objects.equals(climateZone, "E1")) {
                    isLE = 15.305681818181819f;
                }
            } else if (Objects.equals(buildingUse, "vivienda bloque")) {
                if (Objects.equals(climateZone, "B3")) {
                    isLE = 20.175550755939526f;
                } else if (Objects.equals(climateZone, "C2")) {
                    isLE = 16.002053445850912f;
                } else if (Objects.equals(climateZone, "C3")) {
                    isLE = 14.086790123456788f;
                } else if (Objects.equals(climateZone, "D1")) {
                    isLE = 12.533221476510066f;
                } else if (Objects.equals(climateZone, "D2")) {
                    isLE = 12.963865979381442f;
                } else if (Objects.equals(climateZone, "D3")) {
                    isLE = 19.022884615384616f;
                } else if (Objects.equals(climateZone, "E1")) {
                    isLE = 15.662268907563023f;
                }
            }

            float c1NRPE = calculateIndex1(rnRPE, ioNonRenewablePrimaryEnergy, irNRPE);
            float c2NRPE = calculateIndex2(rsNRPE, ioNonRenewablePrimaryEnergy, isNRPE);
            Qualification nonRenewablePrimaryQualification = findQualification(c1NRPE, c2NRPE);

            float c1CO2E = calculateIndex1(rcO2E, ioCO2E, irCO2E);
            float c2NCO2E = calculateIndex2(rsCO2E, ioCO2E, isCO2E);
            Qualification co2Qualification = findQualification(c1CO2E, c2NCO2E);

            float c1HE = calculateIndex1(rhE, ioHeating, irHE);
            float c2HE = calculateIndex2(rsHE, ioHeating, isHE);
            Qualification heatingQualification = findQualification(c1HE, c2HE);

            float c1RE = calculateIndex1(rrE, ioRefrigeration, irRE);
            float c2RE = calculateIndex2(rsHE, ioRefrigeration, isRE);
            Qualification refrigerationQualification = findQualification(c1RE, c2RE);

            float c1acsE = calculateIndex1(raCSE, ioACS, iraCSE);
            float c2acsE = calculateIndex2(rsaCSE, ioACS, isaCSE);
            Qualification acsQualification = findQualification(c1acsE, c2acsE);

            float c1LE = calculateIndex1(rlE, ioLighting, irLE);
            float c2LE = calculateIndex2(rsLE, ioLighting, isLE);
            Qualification lightingQualification = findQualification(c1LE, c2LE);

            return new CalculatorResultsDTO(null, ioNonRenewablePrimaryEnergy,
                    ioCO2E, ioHeating, ioRefrigeration, ioACS, ioLighting, nonRenewablePrimaryQualification,
                    co2Qualification, heatingQualification,
                    refrigerationQualification, acsQualification, lightingQualification);
        }
    }
}
