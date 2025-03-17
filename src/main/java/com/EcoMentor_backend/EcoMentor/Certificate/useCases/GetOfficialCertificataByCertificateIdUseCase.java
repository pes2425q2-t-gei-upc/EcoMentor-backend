package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.OfficialCertificateWFE;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.OfficialCertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetOfficialCertificataByCertificateIdUseCase {
    private final CertificateRepository certificateRepository;
    private final OfficialCertificateMapper certificateMapper;

    public GetOfficialCertificataByCertificateIdUseCase(CertificateRepository certificateRepository, OfficialCertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }
    public OfficialCertificateWFE execute(Long certificateId) {
        Certificate certificate = certificateRepository.findByCertificateId(certificateId);
        if (certificate instanceof OfficialCertificate officialCertificate) {
            OfficialCertificate oc = (OfficialCertificate) certificate;
            return certificateMapper.OfficialCertificateWFEToDTO(oc);
        }
        return null;
    }

}