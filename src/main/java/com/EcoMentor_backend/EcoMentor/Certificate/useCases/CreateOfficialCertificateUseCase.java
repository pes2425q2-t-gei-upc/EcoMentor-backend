package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import com.EcoMentor_backend.EcoMentor.Address.useCases.CreateAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateOfficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateOfficialCertificateUseCase {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final CreateAddressUseCase createAddressUseCase;
    private final OfficialCertificateRepository officialCertificateRepository;

    public CreateOfficialCertificateUseCase(CertificateRepository certificateRepository, CertificateMapper certificateMapper
            , CreateAddressUseCase createAddressUseCase, OfficialCertificateRepository OfficialCertificateRepository) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
        this.createAddressUseCase = createAddressUseCase;
        this.officialCertificateRepository = OfficialCertificateRepository;
    }

    public void execute(CreateOfficialCertificateDTO certificateDTO) {
        CreateAddressDTO createAddressDTO = certificateDTO.getCreateAddressDTO();
        createAddressUseCase.execute(createAddressDTO);
        if (officialCertificateRepository.existsOfficialCertificateByDocumentId(certificateDTO.getDocumentId())) {
            throw new RuntimeException("Official Certificate already exists");
        }
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificateRepository.save(certificate);
        System.out.println("Official Certificate created" + certificate);
    }
}
