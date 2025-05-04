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
        // Lista de certificados simulada
        List<Certificate> certificates = new ArrayList<>();
        Certificate certificate = new Certificate();  // Asegúrate de crear un certificado de prueba
        certificates.add(certificate);

        // Lista de DTOs esperados
        List<CertificateWithoutForeignEntitiesDTO> certificateDTOS = new ArrayList<>();
        CertificateWithoutForeignEntitiesDTO certificateWithoutForeignEntitiesDTO = new CertificateWithoutForeignEntitiesDTO();  // Crear un DTO de prueba
        certificateDTOS.add(certificateWithoutForeignEntitiesDTO);

        List<Object> values = List.of("value1", "value2");

        // Configura el mock para devolver los certificados simulados cuando se busquen por el conjunto de valores
        when(certificateRepository.findCertificateBySetOfValues("parameter", values)).thenReturn(certificates);

        // Configura el mock para el mapeo de certificado a DTO
        when(certificateMapper.toDTOW(certificate)).thenReturn(certificateWithoutForeignEntitiesDTO);

        // Llama al método que estás probando
        List<CertificateWithoutForeignEntitiesDTO> result = getCertificateBySetOfValuesUseCase.execute("parameter", values);

        // Compara los resultados
        assertEquals(certificateDTOS.size(), result.size());  // Verifica que las listas tengan el mismo tamaño

        // Verifica que se haya llamado al repositorio con los parámetros correctos
        verify(certificateRepository, times(1)).findCertificateBySetOfValues("parameter", values);
    }

}