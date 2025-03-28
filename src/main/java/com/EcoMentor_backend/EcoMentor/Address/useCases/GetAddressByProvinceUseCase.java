package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GetAddressByProvinceUseCase {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public GetAddressByProvinceUseCase(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public List<AddressDTO> execute(String province) {
        List<Address> addresses = addressRepository.findByProvince(province);
        List<AddressDTO> listAddressDTO = new ArrayList<>();
        for (Address address : addresses) {
            listAddressDTO.add(addressMapper.toDTO(address));
        }
        return listAddressDTO;
    }
}
