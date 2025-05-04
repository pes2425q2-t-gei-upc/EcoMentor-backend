package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAllRegionsUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.RegionsDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GetAllRegionsUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private GetAllRegionsUseCase getAllRegionsUseCase;

    GetAllRegionsUseCaseTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Returns all distinct regions when repository has data")
    @Test
    void returnsAllDistinctRegionsWhenRepositoryHasData() {
        List<String> regions = List.of("Region1", "Region2", "Region3");
        when(addressRepository.findDistinctRegions()).thenReturn(regions);

        RegionsDTO result = getAllRegionsUseCase.execute();

        assertEquals(regions, result.getRegions());
    }

    @DisplayName("Returns empty list when repository has no data")
    @Test
    void returnsEmptyListWhenRepositoryHasNoData() {
        when(addressRepository.findDistinctRegions()).thenReturn(Collections.emptyList());

        RegionsDTO result = getAllRegionsUseCase.execute();

        assertEquals(Collections.emptyList(), result.getRegions());
    }
}