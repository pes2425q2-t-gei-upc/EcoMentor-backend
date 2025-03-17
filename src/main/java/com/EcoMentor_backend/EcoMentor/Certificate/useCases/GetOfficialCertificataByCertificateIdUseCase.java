package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.BorrarOfficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.OfficialCertificateWFE;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.MapperBorrar;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.OfficialCertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetOfficialCertificataByCertificateIdUseCase {
    private final CertificateRepository certificateRepository;
    private final OfficialCertificateMapper certificateMapper;
    private final MapperBorrar mapperBorrar;

    public GetOfficialCertificataByCertificateIdUseCase(CertificateRepository certificateRepository, OfficialCertificateMapper certificateMapper, MapperBorrar mapperBorrar) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
        this.mapperBorrar = mapperBorrar;
    }
    public BorrarOfficialCertificateDTO execute(Long certificateId) {
        Certificate certificate = certificateRepository.findByCertificateId(certificateId);
        if (certificate instanceof OfficialCertificate officialCertificate) {
            OfficialCertificate oc = (OfficialCertificate) certificate;
            return mapperBorrar.toDTO(oc);
        }
        return null;
    }

}