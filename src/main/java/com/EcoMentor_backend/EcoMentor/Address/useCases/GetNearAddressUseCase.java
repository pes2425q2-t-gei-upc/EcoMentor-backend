package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GetNearAddressUseCase {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public GetNearAddressUseCase(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public List<AddressDTO> execute(double radius, double latitude, double longitude) {
        Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        List<Address> listAddress = addressRepository.findAddressesWithinDistance(location, radius);
        if (listAddress.isEmpty()) {
            throw new RuntimeException("No address found within the radius");
        }

        List<AddressDTO> listAddressDTO = new ArrayList<>();
        for (Address address : listAddress) {
            listAddressDTO.add(addressMapper.toDTO(address));
        }
        return listAddressDTO;
    }
}
