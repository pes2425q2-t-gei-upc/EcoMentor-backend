package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import com.EcoMentor_backend.EcoMentor.Address.useCases.CreateAddressUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateCertificateUseCase {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final CreateAddressUseCase createAddressUseCase;

    public CreateCertificateUseCase(CertificateRepository certificateRepository, CertificateMapper certificateMapper, CreateAddressUseCase createAddressUseCase) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
        this.createAddressUseCase = createAddressUseCase;
    }

    public void execute(CreateCertificateDTO certificateDTO) {
        CreateAddressDTO createAddressDTO = certificateDTO.getCreateAddressDTO();
        createAddressUseCase.execute(createAddressDTO);
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificateRepository.save(certificate);
        System.out.println("Certificate created" + certificate);
    }
}
