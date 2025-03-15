package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateAddressUseCase {
    private final AddressRepository addressRepository;
    private final CertificateRepository certificateRepository;
    private final AddressMapper addressMapper;

    public CreateAddressUseCase(AddressRepository addressRepository, AddressMapper addressMapper, CertificateRepository certificateRepository) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.certificateRepository = certificateRepository;
    }

    public Long execute(CreateAddressDTO createAddressDTO) {
        if(addressRepository.existsAddressByAddressNameAndAddressNumber(createAddressDTO.getAddressName(), createAddressDTO.getAddressNumber())) {
            throw new RuntimeException("Address already exists");
        }

        Address address = addressMapper.toEntity(createAddressDTO);
        addressRepository.save(address);
        return address.getAddressId();
    }
}
