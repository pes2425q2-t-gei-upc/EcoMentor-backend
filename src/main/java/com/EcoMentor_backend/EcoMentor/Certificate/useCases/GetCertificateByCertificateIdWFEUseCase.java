package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetCertificateByCertificateIdWFEUseCase {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    public GetCertificateByCertificateIdWFEUseCase(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    public CertificateWithoutForeignEntitiesDTO execute(Long certificateId) {
        Certificate certificate = certificateRepository.findCertificateByCertificateId(certificateId);
        if(certificate == null) {
            throw new RuntimeException("Certificate not found");
        }
        return certificateMapper.toDTOW(certificate);
    }
}
