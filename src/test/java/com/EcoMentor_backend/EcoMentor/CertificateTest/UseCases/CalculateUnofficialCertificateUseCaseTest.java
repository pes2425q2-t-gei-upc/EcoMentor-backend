package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyFloat;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.AddCertificateToAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.CreateAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.CalculateUnofficialCertificateUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculateUnofficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculatorResultsDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateUnofficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
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

    @InjectMocks
    private CalculateUnofficialCertificateUseCase calculateUnofficialCertificateUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        // Arrange
        CalculateUnofficialCertificateDTO calculateUnofficialCertificateDTO = new CalculateUnofficialCertificateDTO();
        calculateUnofficialCertificateDTO.setSolarThermal(true);
        calculateUnofficialCertificateDTO.setPhotovoltaicSolar(false);
        calculateUnofficialCertificateDTO.setBiomass(true);
        calculateUnofficialCertificateDTO.setDistrictNet(false);
        calculateUnofficialCertificateDTO.setBuildingUse("Residential");
        calculateUnofficialCertificateDTO.setNonRenewablePrimaryEnergyAprox(100);
        calculateUnofficialCertificateDTO.setHeatingEmissionsAprox(50);
        calculateUnofficialCertificateDTO.setRefrigerationEmissionsAprox(30);
        calculateUnofficialCertificateDTO.setAcsEmissionsAprox(20);
        calculateUnofficialCertificateDTO.setLightingEmissionsAprox(10);
        calculateUnofficialCertificateDTO.setClimateZone("B3");
        calculateUnofficialCertificateDTO.setGeothermal(false);
        calculateUnofficialCertificateDTO.setInsulation(1.0f);
        calculateUnofficialCertificateDTO.setWindowEfficiency(1.5f);
        calculateUnofficialCertificateDTO.setResidentialUseVentilation(0.8f);
        // Configuración correcta de CreateAddressDTO
        CreateAddressDTO createAddressDTO = CreateAddressDTO.builder()
                .addressName("Street Name")
                .addressNumber("123")
                .zipcode(12345)
                .town("Town Name")
                .region("Region Name")
                .province("Province Name")
                .longitude(10.1234f)
                .latitude(20.5678f)
                .build();
        calculateUnofficialCertificateDTO.setCreateAddressDTO(createAddressDTO);

        CreateAddressDTO createAddressDTO2 = calculateUnofficialCertificateDTO.getCreateAddressDTO();
        Long addressId = 1L;

        when(addressRepository.existsAddressByAddressNameAndAddressNumber(createAddressDTO2.getAddressName(),
                createAddressDTO2.getAddressNumber())).thenReturn(false);
        when(createAddressUseCase.execute(createAddressDTO2)).thenReturn(addressId);

        CalculatorResultsDTO calculatorResultsDTO = new CalculatorResultsDTO();
        calculatorResultsDTO.setIoNonRenewablePrimaryEnergy(80.0f);
        calculatorResultsDTO.setIoCO2E(40.0f);
        calculatorResultsDTO.setIoHeating(20.0f);
        calculatorResultsDTO.setIoRefrigeration(10.0f);
        calculatorResultsDTO.setIoACS(5.0f);
        calculatorResultsDTO.setIoLighting(2.0f);
        calculatorResultsDTO.setNonRenewablePrimaryQualification(Qualification.A);
        calculatorResultsDTO.setCo2Qualification(Qualification.B);

        when(certificateRepository.calculateQualifications(anyString(), anyString(), anyInt(), anyBoolean(),
                anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyFloat(), anyFloat(), anyFloat(),
                anyFloat(), anyFloat(), anyFloat(), anyFloat())).thenReturn(calculatorResultsDTO);

        Certificate certificate = new Certificate();
        when(certificateMapper.toEntity(any(CreateUnofficialCertificateDTO.class))).thenReturn(certificate);

        // Act
        CalculatorResultsDTO result = calculateUnofficialCertificateUseCase.execute(calculateUnofficialCertificateDTO);

        // Assert
        assertNotNull(result);
        verify(addressRepository, times(1)).existsAddressByAddressNameAndAddressNumber(anyString(), anyString());
        verify(createAddressUseCase, times(1)).execute(createAddressDTO);
        verify(certificateRepository, times(1)).calculateQualifications(anyString(), anyString(), anyInt(),
                anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean(), anyFloat(), anyFloat(),
                anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(certificateMapper, times(1)).toEntity(any(CreateUnofficialCertificateDTO.class));
        verify(certificateRepository, times(1)).save(certificate);
        verify(addCertificateToAddressUseCase, times(1)).execute(addressId, certificate.getCertificateId());
    }
}