package com.EcoMentor_backend.EcoMentor.Certificate.useCases;


import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GetCertificateByHeatingQualificationUseCase {
    private final OfficialCertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    public GetCertificateByHeatingQualificationUseCase(OfficialCertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }
    public List<CertificateDTO> execute(Qualification heatingQualification) {
        List<OfficialCertificate> certificates = certificateRepository.findCertificateByHeatingQualification(heatingQualification);
        List<CertificateDTO> certificateDTOs = new ArrayList<>();
        for (OfficialCertificate certificate : certificates) {
            certificateDTOs.add(certificateMapper.toDTO(certificate));
        }
        return certificateDTOs;
    }
}
