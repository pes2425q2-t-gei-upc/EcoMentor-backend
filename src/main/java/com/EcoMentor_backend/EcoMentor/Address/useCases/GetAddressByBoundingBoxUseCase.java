package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOWithOfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GetAddressByBoundingBoxUseCase {
    AddressRepository addressRepository;
    AddressMapper addressMapper;

    public GetAddressByBoundingBoxUseCase(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public List<AddressDTOWithOfficialCertificate> execute(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {
        List<Address> addresses = addressRepository.findAddressesWithinBoundingBox(minLatitude, maxLatitude, minLongitude, maxLongitude);
        List<AddressDTOWithOfficialCertificate> listAddressDTO = new ArrayList<>();
        for (Address address : addresses) {
            if (true) {
                listAddressDTO.add(addressMapper.toDTOWithOfficialCertificate(address));
            }
        }
        return listAddressDTO;
    }

    private Boolean hasOfficialCertificates(Address address) {
        for (Certificate certificate : address.getCertificates()) {
            if (certificate.getCertificateType() == CertificateType.OFFICIAL) {
                return true;
            }
        }
        return false;
    }

}
