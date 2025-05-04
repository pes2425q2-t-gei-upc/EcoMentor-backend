package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetMultipleFiltersAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOSimple;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GetMultipleFiltersAddressUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private CertificateRepository certificateRepository;

    @InjectMocks
    private GetMultipleFiltersAddressUseCase getMultipleFiltersAddressUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        // Datos de prueba
        Map<String, String> filters = Map.of("key1", "value1", "key2", "value2");
        double minLatitude = 10.0;
        double maxLatitude = 20.0;
        double minLongitude = 30.0;
        double maxLongitude = 40.0;

        Map<String, Object> typedParams = Map.of("key1", "value1Typed", "key2", "value2Typed");
        Address address = new Address();
        AddressDTOSimple addressDTOSimple = new AddressDTOSimple();

        // Configuración de mocks
        when(certificateRepository.convertToCorrectType("key1", "value1")).thenReturn("value1Typed");
        when(certificateRepository.convertToCorrectType("key2", "value2")).thenReturn("value2Typed");
        when(addressRepository.findAddressByCertificateByParameters(typedParams, minLatitude, maxLatitude,
                minLongitude, maxLongitude))
                .thenReturn(Collections.singletonList(address));
        when(addressMapper.toDTOSimple(address)).thenReturn(addressDTOSimple);

        // Ejecución del método
        List<AddressDTOSimple> result = getMultipleFiltersAddressUseCase.execute(filters, minLatitude, maxLatitude,
                minLongitude, maxLongitude);

        // Verificaciones
        assertEquals(1, result.size());
        assertEquals(addressDTOSimple, result.getFirst());
        verify(certificateRepository, times(1)).convertToCorrectType("key1", "value1");
        verify(certificateRepository, times(1)).convertToCorrectType("key2", "value2");
        verify(addressRepository, times(1)).findAddressByCertificateByParameters(typedParams,
                minLatitude, maxLatitude, minLongitude, maxLongitude);
        verify(addressMapper, times(1)).toDTOSimple(address);
    }
}
