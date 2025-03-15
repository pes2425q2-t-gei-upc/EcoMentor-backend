package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GetAddressByProvince {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public GetAddressByProvince(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }
    public List<AddressDTO> execute(String province) {
        List<Address> addresses = addressRepository.findByProvince(province);
        List<AddressDTO> list_addressDTO = new ArrayList<>();
        for (Address address : addresses) {
            list_addressDTO.add(addressMapper.toDTO(address));
        }
        return list_addressDTO;
    }
}
