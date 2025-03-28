package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.AddCertificateToUserUseCase;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



class AddCertificateToUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CertificateRepository certificateRepository;

    @InjectMocks
    private AddCertificateToUserUseCase addCertificateToUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void successfullyAddsCertificateToUser() {
        Long userId = 1L;
        Long certificateId = 1L;
        User user = new User();
        Certificate certificate = new Certificate();


        certificate.setUsers(new ArrayList<>());

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(certificateRepository.findCertificateByCertificateId(certificateId)).thenReturn(certificate);

        addCertificateToUserUseCase.execute(userId, certificateId);

        verify(userRepository).save(user);
        verify(certificateRepository).save(certificate);
    }

    @Test
    void throwsExceptionWhenUserNotFound() {
        Long userId = 1L;
        Long certificateId = 1L;
        Certificate certificate = new Certificate();

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());
        when(certificateRepository.findCertificateByCertificateId(certificateId)).thenReturn(certificate);

        assertThrows(IllegalArgumentException.class, () -> addCertificateToUserUseCase.execute(userId, certificateId));
    }

    @Test
    void throwsExceptionWhenCertificateNotFound() {
        Long userId = 1L;
        Long certificateId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(certificateRepository.findCertificateByCertificateId(certificateId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> addCertificateToUserUseCase.execute(userId, certificateId));
    }
}