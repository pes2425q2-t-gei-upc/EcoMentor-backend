package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateAddressUseCase {
    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public CreateAddressUseCase(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public Long execute(CreateAddressDTO createAddressDTO) {
        String addressName = createAddressDTO.getAddressName();
        String addressNumber = createAddressDTO.getAddressNumber();
        if (addressRepository.existsAddressByAddressNameAndAddressNumber(addressName, addressNumber)) {
            Address address = addressRepository.findAddressByAddressNameAndAddressNumber(addressName, addressNumber);
            return address.getAddressId();
        } else {
            Address address = addressMapper.toEntity(createAddressDTO);
            addressRepository.save(address);
            return address.getAddressId();
        }
    }
}
