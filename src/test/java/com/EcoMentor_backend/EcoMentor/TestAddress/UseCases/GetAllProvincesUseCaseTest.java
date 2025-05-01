package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAllProvincesUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.ProvincesDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GetAllProvincesUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private GetAllProvincesUseCase getAllProvincesUseCase;

    GetAllProvincesUseCaseTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Returns all distinct provinces when repository has data")
    @Test
    void returnsAllDistinctProvincesWhenRepositoryHasData() {
        List<String> provinces = List.of("Province1", "Province2", "Province3");
        when(addressRepository.findDistinctProvinces()).thenReturn(provinces);

        ProvincesDTO result = getAllProvincesUseCase.execute();

        assertEquals(provinces, result.getProvinces());
    }

    @DisplayName("Returns empty list when repository has no data")
    @Test
    void returnsEmptyListWhenRepositoryHasNoData() {
        when(addressRepository.findDistinctProvinces()).thenReturn(Collections.emptyList());

        ProvincesDTO result = getAllProvincesUseCase.execute();

        assertEquals(Collections.emptyList(), result.getProvinces());
    }
}
