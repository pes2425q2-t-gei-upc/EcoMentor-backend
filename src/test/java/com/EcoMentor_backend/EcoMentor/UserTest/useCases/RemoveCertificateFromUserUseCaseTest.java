package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.RemoveCertificateFromUserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemoveCertificateFromUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CertificateRepository certificateRepository;

    @InjectMocks
    private RemoveCertificateFromUserUseCase removeCertificateFromUserUseCase;

    @Test
    public void executeRemovesCertificateFromUserSuccessfully() {
        User user = new User();
        Certificate certificate = new Certificate();
        user.getCertificates().add(certificate);
        certificate.getUsers().add(user);

        when(certificateRepository.findCertificateByCertificateId(1L)).thenReturn(certificate);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        removeCertificateFromUserUseCase.execute(1L, 1L);

        assertFalse(user.getCertificates().contains(certificate));
        assertFalse(certificate.getUsers().contains(user));
        verify(userRepository).save(user);
        verify(certificateRepository).save(certificate);
    }

    @Test
    public void executeThrowsNotFoundWhenCertificateDoesNotExist() {
        when(certificateRepository.findCertificateByCertificateId(1L)).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                removeCertificateFromUserUseCase.execute(1L, 1L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Certificate not found", exception.getReason());
    }

    @Test
    public void executeThrowsNotFoundWhenUserDoesNotExist() {
        Certificate certificate = new Certificate();
        when(certificateRepository.findCertificateByCertificateId(1L)).thenReturn(certificate);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                removeCertificateFromUserUseCase.execute(1L, 1L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
    }

    @Test
    public void executeThrowsIllegalArgumentWhenUserDoesNotHaveCertificate() {
        User user = new User();
        Certificate certificate = new Certificate();

        when(certificateRepository.findCertificateByCertificateId(1L)).thenReturn(certificate);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                removeCertificateFromUserUseCase.execute(1L, 1L)
        );

        assertEquals("User does not have this certificate", exception.getMessage());
    }
}