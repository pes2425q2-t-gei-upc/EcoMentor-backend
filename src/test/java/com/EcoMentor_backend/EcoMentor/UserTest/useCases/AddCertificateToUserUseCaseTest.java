package com.EcoMentor_backend.EcoMentor.UserTest.useCases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


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

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            addCertificateToUserUseCase.execute(userId, certificateId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        verify(userRepository).findById(userId);
        verify(certificateRepository).findCertificateByCertificateId(certificateId);
    }


    @Test
    void throwsExceptionWhenCertificateNotFound() {
        Long userId = 1L;
        Long certificateId = 1L;
        User user = new User();

        // Simula que el certificado no se encuentra (esto debería lanzar la excepción y detener la ejecución antes de llegar al userRepository)
        when(certificateRepository.findCertificateByCertificateId(certificateId)).thenReturn(null);

        // No es necesario simular el userRepository si no se alcanza esta parte del código
        // Simula que el usuario existe (esto debería ser llamado solo si el certificado existe)
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        // Verifica que la excepción ResponseStatusException es lanzada correctamente
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            addCertificateToUserUseCase.execute(userId, certificateId);
        });

        // Verifica que la excepción tiene el código de estado adecuado
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Certificate not found"));

        // Verifica que findById no haya sido invocado ya que el flujo de ejecución se detuvo antes
        verify(userRepository, never()).findById(userId);
        verify(certificateRepository).findCertificateByCertificateId(certificateId);
    }



}