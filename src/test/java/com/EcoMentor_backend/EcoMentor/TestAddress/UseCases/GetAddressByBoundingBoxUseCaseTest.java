package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByBoundingBoxUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAddressByBoundingBoxUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private GetAddressByBoundingBoxUseCase getAddressByBoundingBoxUseCase;

    private Address address;
    private AddressDTO addressDTO;
    private Certificate certificate;

    @BeforeEach
    void setUp() {
        certificate = new Certificate();
        certificate.setCertificateType(CertificateType.OFFICIAL);

        address = new Address();
        address.setCertificates(Collections.singletonList(certificate));

        addressDTO = new AddressDTO();
    }

    @Test
    void testExecute() {
        double minLatitude = 10.0;
        double maxLatitude = 20.0;
        double minLongitude = 30.0;
        double maxLongitude = 40.0;

        when(addressRepository.findAddressesWithinBoundingBox(minLatitude, maxLatitude, minLongitude, maxLongitude))
                .thenReturn(Collections.singletonList(address));
        when(addressMapper.toDTO(address)).thenReturn(addressDTO);

        List<AddressDTO> result = getAddressByBoundingBoxUseCase.execute(minLatitude, maxLatitude, minLongitude, maxLongitude);

        assertEquals(1, result.size());
        assertEquals(addressDTO, result.get(0));
    }
}