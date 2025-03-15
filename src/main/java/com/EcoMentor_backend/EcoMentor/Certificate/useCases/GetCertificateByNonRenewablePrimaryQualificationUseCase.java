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
public class GetCertificateByNonRenewablePrimaryQualificationUseCase {
    private final OfficialCertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    public GetCertificateByNonRenewablePrimaryQualificationUseCase(OfficialCertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    public List<CertificateDTO> execute(Qualification NonRenewablePrimaryQualification) {
        List<OfficialCertificate> certificates = certificateRepository.findCertificateByNonRenewablePrimaryQualification(NonRenewablePrimaryQualification);
        List<CertificateDTO> certificateDTOS = new ArrayList<>();
        for (OfficialCertificate certificate : certificates) {
            certificateDTOS.add(certificateMapper.toDTO(certificate));
        }
        return certificateDTOS;
    }
}
