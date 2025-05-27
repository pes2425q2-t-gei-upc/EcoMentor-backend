package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.AchivementProgressUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.AddOfficialCertificateToUserByDocumentIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;

public class AddOfficialCertificateToUserByDocumentIdUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OfficialCertificateRepository officialCertificateRepository;

    @Mock
    private AchivementProgressUseCase achivementProgressUseCase;

    @InjectMocks
    private AddOfficialCertificateToUserByDocumentIdUseCase addOfficialCertificateUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void throwsWhenOfficialCertificateNotFound() {
        Long userId = 1L;
        String docId = "ABC123";
        when(officialCertificateRepository.findOfficialCertificateByDocumentId(docId))
                .thenReturn(null);

        assertThrows(ResponseStatusException.class,
                () -> addOfficialCertificateUseCase.execute(userId, docId)
        );
    }

    @Test
    void throwsWhenUserNotFound() {
        Long userId = 2L;
        String docId = "DEF456";
        OfficialCertificate officialCert = new OfficialCertificate();
        when(officialCertificateRepository.findOfficialCertificateByDocumentId(docId))
                .thenReturn(officialCert);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> addOfficialCertificateUseCase.execute(userId, docId)
        );
    }




}