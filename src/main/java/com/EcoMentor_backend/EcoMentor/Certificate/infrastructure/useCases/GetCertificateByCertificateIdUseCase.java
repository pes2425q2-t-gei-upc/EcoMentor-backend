package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases.mapper.CertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetCertificateByCertificateIdUseCase {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    public GetCertificateByCertificateIdUseCase(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    public CertificateDTO execute(Long certificateId) {
        Certificate certificate = certificateRepository.findCertificateByCertificateId(certificateId);
        return certificateMapper.toDTO(certificate);
    }
}
