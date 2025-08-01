package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.RegionsDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class GetAllRegionsUseCase {

    private final AddressRepository addressRepository;

    public RegionsDTO execute() {
        // obtenemos la lista distinta y la envolvemos en el DTO
        List<String> regions = addressRepository.findDistinctRegions();
        return new RegionsDTO(regions);
    }
}

