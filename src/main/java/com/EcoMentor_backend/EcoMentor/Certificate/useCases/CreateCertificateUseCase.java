package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import com.EcoMentor_backend.EcoMentor.Address.useCases.CreateAddressUserCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateCertificateUseCase {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final CreateAddressUserCase createAddressUserCase;

    public CreateCertificateUseCase(CertificateRepository certificateRepository, CertificateMapper certificateMapper, CreateAddressUserCase createAddressUserCase) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
        this.createAddressUserCase = createAddressUserCase;
    }

    public void execute(CreateCertificateDTO certificateDTO) {
        CreateAddressDTO createAddressDTO = certificateDTO.getCreateAddressDTO();
        createAddressUserCase.execute(createAddressDTO);
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificateRepository.save(certificate);
        System.out.println("Certificate created" + certificate);
    }
}
