package com.EcoMentor_backend.EcoMentor.TestAddress.UseCases;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAllTownsUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.TownsDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GetAllTownsUseCaseTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private GetAllTownsUseCase getAllTownsUseCase;

    GetAllTownsUseCaseTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Returns all distinct towns when repository has data")
    @Test
    void returnsAllDistinctTownsWhenRepositoryHasData() {
        List<String> towns = List.of("Town1", "Town2", "Town3");
        when(addressRepository.findDistinctTowns()).thenReturn(towns);

        TownsDTO result = getAllTownsUseCase.execute();

        assertEquals(towns, result.getTowns());
    }

    @DisplayName("Returns empty list when repository has no data")
    @Test
    void returnsEmptyListWhenRepositoryHasNoData() {
        when(addressRepository.findDistinctTowns()).thenReturn(Collections.emptyList());

        TownsDTO result = getAllTownsUseCase.execute();

        assertEquals(Collections.emptyList(), result.getTowns());
    }
}