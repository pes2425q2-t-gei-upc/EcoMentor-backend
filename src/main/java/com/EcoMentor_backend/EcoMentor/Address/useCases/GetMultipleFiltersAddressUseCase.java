package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOSimple;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GetMultipleFiltersAddressUseCase {
    AddressRepository addressRepository;
    AddressMapper addressMapper;
    CertificateRepository certificateRepository;

    public GetMultipleFiltersAddressUseCase(AddressRepository addressRepository, AddressMapper addressMapper,
                                   CertificateRepository certificateRepository) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.certificateRepository = certificateRepository;
    }

    public List<AddressDTOSimple> execute(Map<String, String> parameters, double minLatitude, double maxLatitude,
                                          double minLongitude, double maxLongitude)                           {



        // Convertir valores al tipo correcto
        Map<String, Object> typedParams = new HashMap<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            Object convertedValue = certificateRepository.convertToCorrectType(entry.getKey(), entry.getValue());
            typedParams.put(entry.getKey(), convertedValue);
        }

        // Buscar direcciones por parámetros
        List<Address> addresses = addressRepository.findAddressByCertificateByParameters(
                typedParams, minLatitude, maxLatitude, minLongitude, maxLongitude
        );

        List<AddressDTOSimple> listAddressDTO = new ArrayList<>();

        for (Address address : addresses) {
            listAddressDTO.add(addressMapper.toDTOSimple(address));
        }
        return listAddressDTO;
    }


}
