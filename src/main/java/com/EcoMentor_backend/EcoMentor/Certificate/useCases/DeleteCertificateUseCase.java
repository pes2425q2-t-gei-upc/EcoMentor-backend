package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteCertificateUseCase {
    private final CertificateRepository certificateRepository;

    public DeleteCertificateUseCase(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public void execute(Long certificateId) {
        Certificate certificate = certificateRepository.findCertificateByCertificateId(certificateId);
        if (certificate == null) {
            throw new RuntimeException("Certificate not found");
        }
        certificateRepository.delete(certificate);
    }
}
