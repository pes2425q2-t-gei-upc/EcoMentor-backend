package com.EcoMentor_backend.EcoMentor.UserTest.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserTest {

    @Mock
    private Certificate certificate;

    @InjectMocks
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password")
                .certificates(new ArrayList<>())
                .build();
    }

    @Test
    void userCreation() {
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertTrue(user.getCertificates().isEmpty());
    }

    @Test
    void userWithCertificates() {
        List<Certificate> certificates = new ArrayList<>();
        certificates.add(certificate);

        user.setCertificates(certificates);

        assertNotNull(user);
        assertEquals(1, user.getCertificates().size());
        assertEquals(certificate, user.getCertificates().get(0));
    }

    @Test
    void userWithoutCertificates() {
        assertNotNull(user);
        assertTrue(user.getCertificates().isEmpty());
    }

    @Test
    void userDetailsMethods() {
        assertEquals("john.doe@example.com", user.getUsername());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }
}