package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.AddCertificateToAddressUseCase;
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
    private final AddCertificateToAddressUseCase addCertificateToAddressUseCase;
    private final AddressRepository addressRepository;

    public CreateCertificateUseCase(CertificateRepository certificateRepository, CertificateMapper certificateMapper,
                                    CreateAddressUseCase createAddressUseCase, AddCertificateToAddressUseCase addCertificateToAddressUseCase,
                                    AddressRepository addressRepository) {

        this.addCertificateToAddressUseCase = addCertificateToAddressUseCase;
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
        this.createAddressUseCase = createAddressUseCase;
        this.addressRepository = addressRepository;
    }

    public void execute(CreateCertificateDTO certificateDTO) {
        CreateAddressDTO createAddressDTO = certificateDTO.getCreateAddressDTO();
        Long id;
        if (addressRepository.existsAddressByAddressNameAndAddressNumber(createAddressDTO.getAddressName(), createAddressDTO.getAddressNumber())) {

            id = addressRepository.findAddressByAddressNameAndAddressNumber(createAddressDTO.getAddressName(),createAddressDTO.getAddressNumber()).getAddressId();
        }
        else {
            id = createAddressUseCase.execute(createAddressDTO);
        }
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificateRepository.save(certificate);
        addCertificateToAddressUseCase.execute(id, certificate.getCertificateId());
        System.out.println("Certificate created" + certificate);
    }
}
