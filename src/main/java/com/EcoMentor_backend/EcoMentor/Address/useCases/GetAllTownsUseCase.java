package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.TownsDTO;
import java.util.List;
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
        List<String> towns = addressRepository.findDistinctTowns();
        return new TownsDTO(towns);
    }
}

