package com.EcoMentor_backend.EcoMentor.CertificateTest.UseCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetAllCertificatesUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GetAllCertificatesUseCaseTest {

@Mock
private CertificateRepository certificateRepository;

@Mock
private CertificateMapper certificateMapper;

@InjectMocks
private GetAllCertificatesUseCase getAllCertificatesUseCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}
@Test
void testExecute() {
    // Arrange
    int page = 0;
    int size = 5;
    String sortBy = "name";

    List<Certificate> certificates = IntStream.range(0, size)
            .mapToObj(i -> new Certificate(/* inicializa con datos de prueba */))
            .collect(Collectors.toList());

    Page<Certificate> certificatesPage = new PageImpl<>(certificates);
    when(certificateRepository.findAll(any(Pageable.class))).thenReturn(certificatesPage);

    List<CertificateWithoutForeignEntitiesDTO> dtos = certificates.stream()
            .map(c -> new CertificateWithoutForeignEntitiesDTO(/* inicializa con datos de prueba */))
            .collect(Collectors.toList());

    when(certificateMapper.toDTOW(any(Certificate.class))).thenAnswer(invocation -> {
        Certificate certificate = invocation.getArgument(0);
        return new CertificateWithoutForeignEntitiesDTO(/* inicializa con datos de prueba */);
    });

    // Act
    Page<CertificateWithoutForeignEntitiesDTO> result = getAllCertificatesUseCase.execute(page, size, sortBy);

    // Assert
    assertEquals(dtos.size(), result.getContent().size());
    verify(certificateRepository, times(1)).findAll(any(Pageable.class));
    verify(certificateMapper, times(certificates.size())).toDTOW(any(Certificate.class));
}

}
