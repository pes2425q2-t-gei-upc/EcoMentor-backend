package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyFloat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.AchivementProgressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.AddCertificateToAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.CreateAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.CalculateUnofficialCertificateUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.KindOfHeating;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.KindOfRefrigeration;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculateUnofficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculatorResultsDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateUnofficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import java.time.Year;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CalculateUnofficialCertificateUseCaseTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private AddCertificateToAddressUseCase addCertificateToAddressUseCase;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CreateAddressUseCase createAddressUseCase;

    @Mock
    private CertificateMapper certificateMapper;

    @Mock
    private AchivementProgressUseCase achievementProgressUseCase;

    @InjectMocks
    private CalculateUnofficialCertificateUseCase calculateUnofficialCertificateUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {

        CreateAddressDTO createAddressDTO = CreateAddressDTO.builder()
                .addressName("Street")
                .addressNumber("123")
                .zipcode(12345)
                .town("Town")
                .region("Region")
                .province("Province")
                .longitude(10.0f)
                .latitude(20.0f)
                .build();

        // Mock de datos de entrada
        CalculateUnofficialCertificateDTO inputDTO = new CalculateUnofficialCertificateDTO();
        inputDTO.setCreateAddressDTO(createAddressDTO);
        inputDTO.setFloor("3");
        inputDTO.setDoor("B");
        inputDTO.setClimateZone("Zone1");
        inputDTO.setBuildingYear(Year.of(2020));
        inputDTO.setBuildingUse("Residential");
        inputDTO.setCadastreMeters(100);
        inputDTO.setAnnualCost(1000.0f);
        inputDTO.setElectricVehicle(true);
        inputDTO.setSolarThermal(false);
        inputDTO.setPhotovoltaicSolar(true);
        inputDTO.setKindOfHeating(KindOfHeating.GAS);
        inputDTO.setKindOfRefrigeration(KindOfRefrigeration.DISTRICTE);
        inputDTO.setKindOfAcs(KindOfHeating.ELECTRICA);
        inputDTO.setInsulation(10);
        inputDTO.setWindowEfficiency(5);
        inputDTO.setResidentialUseVentilation(1);
        inputDTO.setEnergeticRehabilitation(true);
        inputDTO.setHeatingConsumption(50.0f);
        inputDTO.setRefrigerationConsumption(20.0f);
        inputDTO.setAcsConsumption(10.0f);
        inputDTO.setLightingConsumption(5.0f);



        // Mock de repositorios y dependencias
        when(addressRepository.existsAddressByAddressNameAndAddressNumber("Street", "123")).thenReturn(false);
        when(createAddressUseCase.execute(any(CreateAddressDTO.class))).thenReturn(1L);
        when(certificateRepository.calculateAproxInsulation(10, "Residential")).thenReturn(8.0f);
        when(certificateRepository.calculateAproxWindowEfficiciency(5, "Residential")).thenReturn(4.0f);
        when(certificateRepository.calculateAproxResidentialUseVentilation(1, "Residential")).thenReturn(0.8f);

        CalculatorResultsDTO resultsDTO = new CalculatorResultsDTO();
        resultsDTO.setIoNonRenewablePrimaryEnergy(50.0f);
        resultsDTO.setIoCO2E(20.0f);
        resultsDTO.setIoHeating(15.0f);
        resultsDTO.setIoRefrigeration(10.0f);
        resultsDTO.setIoACS(5.0f);
        resultsDTO.setIoLighting(2.0f);
        resultsDTO.setNonRenewablePrimaryQualification(Qualification.A);
        resultsDTO.setCo2Qualification(Qualification.B);
        resultsDTO.setHeatingQualification(Qualification.C);
        resultsDTO.setRefrigerationQualification(Qualification.D);
        resultsDTO.setAcsQualification(Qualification.E);
        resultsDTO.setLightingQualification(Qualification.F);

        when(certificateRepository.calculateQualificationsForANewCertificate(
                anyString(), anyString(), anyBoolean(), anyBoolean(), anyFloat(), anyFloat(), anyFloat(),
                any(KindOfHeating.class), any(KindOfHeating.class), any(KindOfRefrigeration.class),
                anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat()
        )).thenReturn(resultsDTO);

        Certificate certificate = new Certificate();
        when(certificateMapper.toEntity(any(CreateUnofficialCertificateDTO.class))).thenReturn(certificate);

        // Ejecución del caso de uso
        long userId = 1L;
        CalculatorResultsDTO result = calculateUnofficialCertificateUseCase.execute(inputDTO, userId);

        // Verificaciones
        assertNotNull(result);
        verify(certificateRepository, times(1)).save(certificate);
        verify(addCertificateToAddressUseCase, times(1)).execute(1L, certificate.getCertificateId());
        verify(achievementProgressUseCase, times(1)).execute(userId, 5L);
    }
}