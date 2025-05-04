package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAllAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.*;

import java.util.List;

public class GetAllAddressUseCaseTest {

@Mock
private AddressRepository addressRepository;

@Mock
private AddressMapper addressMapper;

@InjectMocks
private GetAllAddressUseCase getAllAddressUseCase;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void testExecute_ReturnsMappedPageWithSort() {
    // Arrange
    int page = 0;
    int size = 2;
    String sortField = "city";  // por ejemplo
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortField));

    Address addr1 = new Address();
    Address addr2 = new Address();

    AddressDTO dto1 = new AddressDTO();
    AddressDTO dto2 = new AddressDTO();

    Page<Address> addressPage = new PageImpl<>(List.of(addr1, addr2), pageable, 2);

    when(addressRepository.findAll(pageable)).thenReturn(addressPage);
    when(addressMapper.toDTO(any(Address.class)))
            .thenReturn(dto1)
            .thenReturn(dto2);

    // Act
    Page<AddressDTO> result = getAllAddressUseCase.execute(page, size, sortField);

    // Assert
    assertEquals(2, result.getContent().size());
    assertEquals(dto1, result.getContent().get(0));
    assertEquals(dto2, result.getContent().get(1));

    verify(addressRepository, times(1)).findAll(pageable);
    verify(addressMapper, times(2)).toDTO(any(Address.class));
    verifyNoMoreInteractions(addressRepository, addressMapper);
}
}
