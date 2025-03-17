// src/test/java/com/EcoMentor_backend/EcoMentor/Certificate/entity/CertificateTest.java
package com.EcoMentor_backend.EcoMentor.Certificate.entity;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CertificateTest {

    @Mock
    private Address address;
    @Mock
    private Recommendation recommendation;
    @Mock
    private Recommendation recommendation2;

    private Certificate certificate;

    @BeforeEach
    void setUp() {
        address = mock(Address.class);
        recommendation = mock(Recommendation.class);
        certificate = Certificate.builder()
                .certificateId(1L)
                .certificateType(CertificateType.OFFICIAL)
                .address(address)
                .recommendations(new ArrayList<>())
                .build();
    }

    @Test
    void certificateShouldHaveValidId() {
        assertNotNull(certificate.getCertificateId());
    }

    @Test
    void certificateShouldHaveValidType() {
        assertNotNull(certificate.getCertificateType());
    }

    @Test
    void certificateShouldHaveAddress() {
        assertNotNull(certificate.getAddress());
    }

    @Test
    void certificateShouldHaveEmptyRecommendationsInitially() {
        assertTrue(certificate.getRecommendations().isEmpty());
    }

    @Test
    void certificateShouldAddRecommendation() {
        certificate.getRecommendations().add(recommendation);
        assertEquals(1, certificate.getRecommendations().size());
    }

    @Test
    void certificateShouldHandleNullRecommendations() {
        certificate.setRecommendations(null);
        assertNull(certificate.getRecommendations());
    }

    @Test
    void certificateShouldRemoveRecommendation() {
        certificate.getRecommendations().add(recommendation);
        certificate.getRecommendations().remove(recommendation);
        assertTrue(certificate.getRecommendations().isEmpty());
    }

    @Test
    void certificateShouldNotAddDuplicateRecommendations() {
        certificate.getRecommendations().add(recommendation);
        certificate.getRecommendations().add(recommendation);
        assertEquals(1, certificate.getRecommendations().size());
    }

    @Test
    void certificateShouldHandleMultipleRecommendations() {
        certificate.getRecommendations().add(recommendation);
        certificate.getRecommendations().add(recommendation2);
        assertEquals(2, certificate.getRecommendations().size());
    }
}