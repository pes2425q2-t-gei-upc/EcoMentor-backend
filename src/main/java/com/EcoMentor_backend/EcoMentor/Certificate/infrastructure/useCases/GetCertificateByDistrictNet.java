package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases.mapper.CertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GetCertificateByDistrictNet {
    private final CertificateRepository CertificateRepository;
    private final CertificateMapper CertificateMapper;
    private final CertificateMapper certificateMapper;

    public GetCertificateByDistrictNet(CertificateRepository CertificateRepository, CertificateMapper CertificateMapper, CertificateMapper certificateMapper) {
        this.CertificateRepository = CertificateRepository;
        this.CertificateMapper = CertificateMapper;
        this.certificateMapper = certificateMapper;
    }

    public List<CertificateDTO> execute(boolean districtNet) {
        List<Certificate> certificates = CertificateRepository.findCertificateByDistrictNet(districtNet);
        List<CertificateDTO> certificateDTOS = new ArrayList<>();
        for (Certificate certificate : certificates) {
            certificateDTOS.add(certificateMapper.toDTO(certificate));
        }
        return certificateDTOS;
    }
}
