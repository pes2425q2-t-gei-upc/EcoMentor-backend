package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class GetCertificateByParameterUseCase {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    public GetCertificateByParameterUseCase(CertificateRepository certificateRepository,
                                            CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    public List<CertificateWithoutForeignEntitiesDTO> execute(String parameter, String value, double minLatitude,
                                                              double maxLatitude, double minLongitude,
                                                              double maxLongitude) {
        Object correctValue = certificateRepository.convertToCorrectType(parameter, value);
        List<Certificate> certificates = certificateRepository.findCertificateByParameter(parameter, correctValue,
                minLatitude, maxLatitude, minLongitude, maxLongitude);
        if (certificates.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found");
        }
        List<CertificateWithoutForeignEntitiesDTO> certificateDTOS = new ArrayList<>();
        for (Certificate certificate : certificates) {
            certificateDTOS.add(certificateMapper.toDTOW(certificate));
        }
        return certificateDTOS;
    }
}
