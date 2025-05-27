package com.EcoMentor_backend.EcoMentor.RecommendationTest.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.CalculateRecommendationValuesUseCase;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.CreateRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.TotalValuesRecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.AchivementProgressUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class CalculateRecommendationValuesUseCaseTest {

    @Mock
    private RecommendationMapper recommendationMapper;

    @Mock
    private AchivementProgressUseCase achievementProgressUseCase;

    @Mock
    private OfficialCertificateRepository certificateRepository;

    @Mock
    private AchivementProgressUseCase achivementProgressUseCase;

    @InjectMocks
    private CalculateRecommendationValuesUseCase calculateUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void throwsWhenCertificateNotFound() {
        long certId = 10L;
        long userId = 20L;
        when(certificateRepository.findById(certId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> calculateUseCase.calculateValues(List.of(), certId, userId)
        );
    }

}

