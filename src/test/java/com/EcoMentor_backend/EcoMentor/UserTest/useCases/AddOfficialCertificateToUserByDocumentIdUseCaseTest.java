package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.AddOfficialCertificateToUserByDocumentIdUseCase;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class AddOfficialCertificateToUserByDocumentIdUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OfficialCertificateRepository officialCertificateRepository;

    @InjectMocks
    private AddOfficialCertificateToUserByDocumentIdUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeAddsOfficialCertificateToUserSuccessfully() {
        Long userId = 1L;
        String documentId = "DOC123456";
        User user = new User();
        OfficialCertificate officialCertificate = new OfficialCertificate();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(officialCertificateRepository.findOfficialCertificateByDocumentId(documentId))
                .thenReturn(officialCertificate);

        useCase.execute(userId, documentId);

        verify(userRepository).findById(userId);
        verify(officialCertificateRepository).findOfficialCertificateByDocumentId(documentId);
        verify(officialCertificateRepository).save(officialCertificate);
        verify(userRepository).save(user);
    }

    @Test
    void executeThrowsNotFoundWhenOfficialCertificateNotFound() {
        Long userId = 1L;
        String documentId = "DOC123456";

        when(officialCertificateRepository.findOfficialCertificateByDocumentId(documentId)).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            useCase.execute(userId, documentId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        verify(officialCertificateRepository).findOfficialCertificateByDocumentId(documentId);
        verify(userRepository, never()).findById(anyLong());
        verify(officialCertificateRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }



    @Test
    void executeThrowsNotFoundWhenUserNotFound() {
        Long userId = 1L;
        String documentId = "DOC123456";
        OfficialCertificate officialCertificate = new OfficialCertificate();

        when(officialCertificateRepository.findOfficialCertificateByDocumentId(documentId))
                .thenReturn(officialCertificate);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            useCase.execute(userId, documentId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("User not found"));

        verify(officialCertificateRepository).findOfficialCertificateByDocumentId(documentId);
        verify(userRepository).findById(userId);
        verify(officialCertificateRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }

}