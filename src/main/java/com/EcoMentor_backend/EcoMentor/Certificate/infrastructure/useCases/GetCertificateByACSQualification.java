package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases.mapper.CertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GetCertificateByACSQualification {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    public GetCertificateByACSQualification(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

public List<CertificateDTO> execute(Qualification ACSQualification) {
        List<Certificate> certificates = certificateRepository.findCertificateByACSQualification(ACSQualification);
        List<CertificateDTO> certificateDTOS = new ArrayList<>();
        for (Certificate certificate : certificates) {
            certificateDTOS.add(certificateMapper.toDTO(certificate));
        }
        return certificateDTOS;
}
}
