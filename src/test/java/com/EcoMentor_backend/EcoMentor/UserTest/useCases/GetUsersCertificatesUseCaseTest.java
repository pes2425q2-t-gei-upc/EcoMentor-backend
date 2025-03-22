package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUsersCertificatesUseCase;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



class GetUsersCertificatesUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @InjectMocks
    private GetUsersCertificatesUseCase getUsersCertificatesUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return list of CertificateDTO when user has certificates")
    void shouldReturnListOfCertificateDTOWhenUserHasCertificates() {
        User user = new User();
        user.setId(1L);
        CertificateDTO certificateDTO = new CertificateDTO();
        user.setCertificates(Collections.singletonList(new Certificate())); // Ensure user has certificates
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(certificateMapper.toDTO(user.getCertificates().get(0))).thenReturn(certificateDTO);

        List<CertificateDTO> result = getUsersCertificatesUseCase.execute(1L);

        assertEquals(1, result.size());
        assertEquals(certificateDTO, result.get(0));
    }

    @Test
    @DisplayName("Should return empty list when user has no certificates")
    void shouldReturnEmptyListWhenUserHasNoCertificates() {
        User user = new User();
        user.setId(1L);
        user.setCertificates(Collections.emptyList());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<CertificateDTO> result = getUsersCertificatesUseCase.execute(1L);

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Should throw exception when user is not found")
    void shouldThrowExceptionWhenUserIsNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> getUsersCertificatesUseCase.execute(1L));
    }
}