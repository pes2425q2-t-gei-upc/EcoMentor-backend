package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.mapper.RecommendationMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class GetCertificateRecommendationsUseCase {

    private final OfficialCertificateRepository certificateRepository;
    private final RecommendationMapper recommendationMapper;

    public GetCertificateRecommendationsUseCase(OfficialCertificateRepository certificateRepository,
                                                RecommendationMapper recommendationMapper) {
        this.certificateRepository = certificateRepository;
        this.recommendationMapper = recommendationMapper;
    }

    /**
     * Devuelve todas las recomendaciones asociadas a un certificado oficial.
     *
     * @param certificateId Id del certificado oficial
     * @return Lista de RecommendationDTO
     */
    public List<RecommendationDTO> execute(Long certificateId) {
        OfficialCertificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found"));

        List<Recommendation> recommendations = certificate.getRecommendations();

        return recommendations.stream()
                .map(recommendationMapper::toDTO)
                .collect(Collectors.toList());
    }

}
