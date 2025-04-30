package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.TownsDTO;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class GetAllTownsUseCase {

    private final AddressRepository addressRepository;

    public TownsDTO execute() {
        // obtenemos la lista distinta y la envolvemos en el DTO
        return new TownsDTO(new ArrayList<>(addressRepository.findDistinctTowns()));
    }
}

