package com.EcoMentor_backend.EcoMentor.CertificateTest.entity;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CertificateTest {

    @Mock
    private Address address;

    @Mock
    private Recommendation recommendation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCertificateCreation() {
        List<Recommendation> recommendations = new ArrayList<>();
        recommendations.add(recommendation);

        Certificate certificate = Certificate.builder()
                .certificateId(1L)
                .certificateType(CertificateType.OFFICIAL)
                .address(address)
                .recommendations(recommendations)
                .build();

        assertNotNull(certificate);
        assertEquals(1L, certificate.getCertificateId());
        assertEquals(CertificateType.OFFICIAL, certificate.getCertificateType());
        assertEquals(address, certificate.getAddress());
        assertEquals(1, certificate.getRecommendations().size());
        assertEquals(recommendation, certificate.getRecommendations().getFirst());
    }
}