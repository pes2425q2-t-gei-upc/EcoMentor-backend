package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateCertificateUseCase {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    public CreateCertificateUseCase(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    public void execute(CreateCertificateDTO certificateDTO) {
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificateRepository.save(certificate);
        System.out.println("Certificate created" + certificate);
    }
}
