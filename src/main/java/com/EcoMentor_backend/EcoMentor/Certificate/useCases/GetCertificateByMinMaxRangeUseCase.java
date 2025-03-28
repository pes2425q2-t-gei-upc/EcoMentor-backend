package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class GetCertificateByMinMaxRangeUseCase {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    public GetCertificateByMinMaxRangeUseCase(CertificateRepository certificateRepository,
                                              CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    public List<CertificateWithoutForeignEntitiesDTO> execute(String parameter, String minimum, String maximum) {
        Object min = certificateRepository.convertToCorrectType(parameter, minimum);
        Object max = certificateRepository.convertToCorrectType(parameter, maximum);
        List<Certificate> certificates = certificateRepository.findCertificateByMinMaxRange(parameter, min, max);
        List<CertificateWithoutForeignEntitiesDTO> certificateDTOS = new ArrayList<>();
        for (Certificate certificate : certificates) {
            certificateDTOS.add(certificateMapper.toDTOW(certificate));
        }
        return certificateDTOS;
    }
}
