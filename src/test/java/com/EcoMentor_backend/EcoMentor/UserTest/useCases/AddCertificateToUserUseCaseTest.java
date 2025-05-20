package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.AchivementProgressUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.AddCertificateToUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;

public class AddCertificateToUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private AchivementProgressUseCase achivementProgressUseCase;

    @InjectMocks
    private AddCertificateToUserUseCase addCertificateToUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void throwsWhenCertificateNotFound() {
        Long userId = 1L;
        Long certId = 100L;
        when(certificateRepository.findCertificateByCertificateId(certId)).thenReturn(null);

        assertThrows(ResponseStatusException.class,
                () -> addCertificateToUserUseCase.execute(userId, certId)
        );
    }

    @Test
    void throwsWhenUserNotFound() {
        Long userId = 2L;
        Long certId = 200L;
        Certificate cert = new Certificate();
        when(certificateRepository.findCertificateByCertificateId(certId)).thenReturn(cert);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> addCertificateToUserUseCase.execute(userId, certId)
        );
    }




}