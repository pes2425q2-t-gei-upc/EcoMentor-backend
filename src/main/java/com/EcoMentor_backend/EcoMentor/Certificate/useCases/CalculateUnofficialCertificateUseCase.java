package com.EcoMentor_backend.EcoMentor.Certificate.useCases;


import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.AddCertificateToAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.CreateAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOWithoutCertificate;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculateUnofficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculatorResultsDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateUnofficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CalculateUnofficialCertificateUseCase {
    private final CertificateRepository certificateRepository;
    private final AddCertificateToAddressUseCase addCertificateToAddressUseCase;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final CreateAddressUseCase createAddressUseCase;
    private final CertificateMapper certificateMapper;

    public CalculateUnofficialCertificateUseCase(CertificateRepository certificateRepository,
                                                 AddCertificateToAddressUseCase addCertificateToAddressUseCase,
                                                 AddressMapper addressMapper, AddressRepository addressRepository,
                                                 CreateAddressUseCase createAddressUseCase,
                                                 CertificateMapper certificateMapper) {
        // Constructor
        this.certificateRepository = certificateRepository;
        this.addCertificateToAddressUseCase = addCertificateToAddressUseCase;
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
        this.createAddressUseCase = createAddressUseCase;
        this.certificateMapper = certificateMapper;
    }

    public CalculatorResultsDTO execute(CalculateUnofficialCertificateDTO calculateUnofficialCertificateDTO) {
        // Extract parameters from DTO
        boolean solarThermal = calculateUnofficialCertificateDTO.isSolarThermal();
        boolean photovoltaicSolar = calculateUnofficialCertificateDTO.isPhotovoltaicSolar();
        boolean biomass = calculateUnofficialCertificateDTO.isBiomass();

        // Calculate indicators
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

        boolean districtNet = calculateUnofficialCertificateDTO.isDistrictNet();
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
                indicadorBiomassHeating =  0.984f;
                indicadorBiomassACS =  0.984f;
            }
        }

        boolean geothermal = calculateUnofficialCertificateDTO.isGeothermal();
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

        String buildingUse = calculateUnofficialCertificateDTO.getBuildingUse();
        int npREAprox = calculateUnofficialCertificateDTO.getNonRenewablePrimaryEnergyAprox();
        float nonRenewablePrimaryEnergyInitial = certificateRepository.calculateBaseIoNPRE(npREAprox, buildingUse);

        float ioNonRenewablePrimaryEnergy = nonRenewablePrimaryEnergyInitial * indicadorBiomassNRPE
                * indicadorGeothermalNRPE * indicadorDistrictNetNRPE * indicadorPhotovoltaicSolarNRPE
                * indicadorSolarThermalNRPE;

        int heatingAprox = calculateUnofficialCertificateDTO.getHeatingEmissionsAprox();
        float heatingEmissionsInitial = certificateRepository.calculateBaseIoHeating(heatingAprox, buildingUse);;

        float ioHeating = heatingEmissionsInitial * indicadorBiomassHeating
                * indicadorGeothermalHeating * indicadorDistrictNetHeating * indicadorPhotovoltaicSolarHeating
                * indicadorSolarThermalHeating;

        int refrigerationAprox = calculateUnofficialCertificateDTO.getRefrigerationEmissionsAprox();
        float refrigerationEmissionsInitial = certificateRepository
                .calculateBaseIoRefrigeration(refrigerationAprox, buildingUse);;

        float ioRefrigeration = refrigerationEmissionsInitial
                * indicadorGeothermalRefrigeration * indicadorDistrictNetRefrigeration
                * indicadorPhotovoltaicSolarRefrigeration;

        int acsAprox = calculateUnofficialCertificateDTO.getAcsEmissionsAprox();
        float acsEmissionsInitial = certificateRepository.calculateBaseIoACS(acsAprox, buildingUse);;

        float ioACS = acsEmissionsInitial * indicadorBiomassACS * indicadorGeothermalACS
                * indicadorDistrictNetACS * indicadorPhotovoltaicSolarACS * indicadorSolarThermalACS;

        int lightingAprox = calculateUnofficialCertificateDTO.getLightingEmissionsAprox();
        float lightingEmissionsInitial = certificateRepository.calculateBaseIoLighting(lightingAprox, buildingUse);;

        float ioLighting = lightingEmissionsInitial * indicadorPhotovoltaicSolarLighting;
        float ioCO2E = ioHeating + ioRefrigeration + ioACS + ioLighting;


        String climateZone = calculateUnofficialCertificateDTO.getClimateZone();

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
            Qualification nonRenewablePrimaryQualification = certificateRepository.findQualificationT(cnRPE);

            float co2E = ioCO2E / irCO2Em;
            Qualification co2Qualification = certificateRepository.findQualificationT(co2E);

            float heatingE = ioHeating / irHeating;
            Qualification heatingQualification = certificateRepository.findQualificationT(heatingE);

            float refrigerationE = ioRefrigeration / irRefrigeration;
            Qualification refrigerationQualification = certificateRepository.findQualificationT(refrigerationE);

            float acsE = ioACS / irACS;
            Qualification acsQualification = certificateRepository.findQualificationT(acsE);

            float lightingE = ioLighting / irLighting;
            Qualification lightingQualification = certificateRepository.findQualificationT(lightingE);

            return new CalculatorResultsDTO(nonRenewablePrimaryQualification, co2Qualification, heatingQualification,
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


            CreateAddressDTO createAddressDTO = calculateUnofficialCertificateDTO.getCreateAddressDTO();
            Long id;
            if (addressRepository.existsAddressByAddressNameAndAddressNumber(createAddressDTO.getAddressName(),
                    createAddressDTO.getAddressNumber())) {

                id = addressRepository.findAddressByAddressNameAndAddressNumber(createAddressDTO.getAddressName(),
                        createAddressDTO.getAddressNumber()).getAddressId();
            } else {
                id = createAddressUseCase.execute(createAddressDTO);
            }


            CreateUnofficialCertificateDTO unofficialCertificateDTO = new CreateUnofficialCertificateDTO();
            unofficialCertificateDTO.setCertificateType(CertificateType.UNOFFICIAL);
            unofficialCertificateDTO.setCreateAddressDTO(createAddressDTO);
            unofficialCertificateDTO.setFloor(calculateUnofficialCertificateDTO.getFloor());
            unofficialCertificateDTO.setDoor(calculateUnofficialCertificateDTO.getDoor());
            //unofficialCertificateDTO.setCadastreMeters(); //calculateUnofficialCertificateDTO.getCadastreMeters()
            System.out.println("Passa 1");
            unofficialCertificateDTO.setClimateZone(climateZone);
            unofficialCertificateDTO.setBuildingYear(calculateUnofficialCertificateDTO.getBuildingYear());
            unofficialCertificateDTO.setBuildingUse(buildingUse);
            unofficialCertificateDTO.setNonRenewablePrimaryEnergy(ioNonRenewablePrimaryEnergy);

            float c1NRPE = certificateRepository.calculateIndex1(rnRPE, ioNonRenewablePrimaryEnergy, irNRPE);
            float c2NRPE = certificateRepository.calculateIndex2(rsNRPE, ioNonRenewablePrimaryEnergy, isNRPE);
            Qualification nonRenewablePrimaryQualification = certificateRepository.findQualification(c1NRPE, c2NRPE);

            unofficialCertificateDTO.setNonRenewablePrimaryQualification(nonRenewablePrimaryQualification);
            unofficialCertificateDTO.setCo2Emissions(ioCO2E);

            float c1CO2E = certificateRepository.calculateIndex1(rcO2E, ioCO2E, irCO2E);
            float c2NCO2E = certificateRepository.calculateIndex2(rsCO2E, ioCO2E, isCO2E);
            Qualification co2Qualification = certificateRepository.findQualification(c1CO2E, c2NCO2E);

            unofficialCertificateDTO.setCo2Qualification(co2Qualification);
            unofficialCertificateDTO.setFinalEnergyConsumption(calculateUnofficialCertificateDTO
                    .getFinalEnergyConsumption());
            unofficialCertificateDTO.setAnnualCost(calculateUnofficialCertificateDTO.getAnnualCost());
            unofficialCertificateDTO.setElectricVehicle(calculateUnofficialCertificateDTO.isElectricVehicle());
            unofficialCertificateDTO.setSolarThermal(solarThermal);
            unofficialCertificateDTO.setPhotovoltaicSolar(photovoltaicSolar);
            unofficialCertificateDTO.setBiomass(biomass);
            unofficialCertificateDTO.setDistrictNet(districtNet);
            unofficialCertificateDTO.setGeothermal(geothermal);
            unofficialCertificateDTO.setInsulation(calculateUnofficialCertificateDTO.getInsulation());
            unofficialCertificateDTO.setWindowEfficiency(calculateUnofficialCertificateDTO.getWindowEfficiency());
            unofficialCertificateDTO.setHeatingEmissions(ioHeating);

            float c1HE = certificateRepository.calculateIndex1(rhE, ioHeating, irHE);
            float c2HE = certificateRepository.calculateIndex2(rsHE, ioHeating, isHE);
            Qualification heatingQualification = certificateRepository.findQualification(c1HE, c2HE);

            unofficialCertificateDTO.setHeatingQualification(heatingQualification);
            unofficialCertificateDTO.setRefrigerationEmissions(ioRefrigeration);

            float c1RE = certificateRepository.calculateIndex1(rrE, ioRefrigeration, irRE);
            float c2RE = certificateRepository.calculateIndex2(rsHE, ioRefrigeration, isRE);
            Qualification refrigerationQualification = certificateRepository.findQualification(c1RE, c2RE);

            unofficialCertificateDTO.setRefrigerationQualification(refrigerationQualification);
            unofficialCertificateDTO.setAcsEmissions(ioACS);

            float c1acsE = certificateRepository.calculateIndex1(raCSE, ioACS, iraCSE);
            float c2acsE = certificateRepository.calculateIndex2(rsaCSE, ioACS, isaCSE);
            Qualification acsQualification = certificateRepository.findQualification(c1acsE, c2acsE);

            unofficialCertificateDTO.setAcsQualification(acsQualification);
            unofficialCertificateDTO.setLightingEmissions(ioLighting);

            float c1LE = certificateRepository.calculateIndex1(rlE, ioLighting, irLE);
            float c2LE = certificateRepository.calculateIndex2(rsLE, ioLighting, isLE);
            Qualification lightingQualification = certificateRepository.findQualification(c1LE, c2LE);

            unofficialCertificateDTO.setLightingQualification(lightingQualification);
            unofficialCertificateDTO.setResidentialUseVentilation(calculateUnofficialCertificateDTO
                    .getResidentialUseVentilation());
            unofficialCertificateDTO.setEnergeticRehabilitation(calculateUnofficialCertificateDTO
                    .isEnergeticRehabilitation());


            unofficialCertificateDTO.setCreationDate(new java.sql.Date(System.currentTimeMillis()));
            Certificate certificate = certificateMapper.toEntity(unofficialCertificateDTO);
            System.out.println("Passa 2");
            certificateRepository.save(certificate);
            addCertificateToAddressUseCase.execute(id, certificate.getCertificateId());


            return new CalculatorResultsDTO(nonRenewablePrimaryQualification, co2Qualification, heatingQualification,
                    refrigerationQualification, acsQualification, lightingQualification);
        }
    }
}
