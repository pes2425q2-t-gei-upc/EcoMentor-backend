package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOSimple;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GetAddressByBoundingBoxUseCase {
    AddressRepository addressRepository;
    AddressMapper addressMapper;

    public GetAddressByBoundingBoxUseCase(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public List<AddressDTOSimple> execute(double minLatitude, double maxLatitude, double minLongitude,
                                                                                double maxLongitude) {

        List<Address> addresses = addressRepository.findAddressesWithinBoundingBox(minLatitude, maxLatitude,
                                                                                    minLongitude, maxLongitude);

        List<AddressDTOSimple> listAddressDTO = new ArrayList<>();
        for (Address address : addresses) {
            listAddressDTO.add(addressMapper.toDTOSimple(address));
        }
        return listAddressDTO;

    }
}
