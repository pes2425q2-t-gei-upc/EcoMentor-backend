package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateBySetOfValuesUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;





public class GetCertificateBySetOfValuesUseCaseTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @InjectMocks
    private GetCertificateBySetOfValuesUseCase getCertificateBySetOfValuesUseCase;

    @Mock
    private Certificate certificate;

    @Mock
    private CertificateWithoutForeignEntitiesDTO certificateWithoutForeignEntitiesDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        List<Certificate> certificates = new ArrayList<>();
        List<CertificateWithoutForeignEntitiesDTO> certificateDTOS = new ArrayList<>();
        List<Object> values = List.of("value1", "value2");

        when(certificateRepository.findCertificateBySetOfValues("parameter", values)).thenReturn(certificates);
        when(certificateMapper.toDTOW(certificate)).thenReturn(certificateWithoutForeignEntitiesDTO);

        List<CertificateWithoutForeignEntitiesDTO> result = getCertificateBySetOfValuesUseCase.execute("parameter",
                values);

        assertEquals(certificateDTOS.size(), result.size());
        verify(certificateRepository, times(1)).findCertificateBySetOfValues("parameter", values);
    }
}