package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetAddressByAddressIdUserCase {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public GetAddressByAddressIdUserCase(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public AddressDTO execute(Long addressId) {
        Address address = addressRepository.findByAddressId(addressId);
        if(address == null) {
            throw new RuntimeException("Address not found");
        }
        return addressMapper.toDTO(address);
    }
}
