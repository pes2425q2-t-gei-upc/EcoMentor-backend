package com.EcoMentor_backend.EcoMentor.Certificate.useCases;


import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GetCertificateBySolarThermal {
    private final OfficialCertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    public GetCertificateBySolarThermal(OfficialCertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    public List<CertificateDTO> execute(boolean solarThermal) {
        List<OfficialCertificate> certificates = certificateRepository.findCertificateBySolarThermal(solarThermal);
        List<CertificateDTO> certificateDTOS = new ArrayList<>();
        for (OfficialCertificate certificate : certificates) {
            certificateDTOS.add(certificateMapper.toDTO(certificate));
        }
        return certificateDTOS;
    }
}
