package com.EcoMentor_backend.EcoMentor.Address.useCases;
import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GetAllAddressUseCase {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public GetAllAddressUseCase(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }
    public List<AddressDTO> execute() {
        List<Address> address = addressRepository.findAll();
        List<AddressDTO> listAddressDTO = new ArrayList<>();
        for (Address a : address) {
            listAddressDTO.add(addressMapper.toDTO(a));
        }
        return listAddressDTO;
    }
}
