package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.ProvincesDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class GetAllProvincesUseCase {

    private final AddressRepository addressRepository;

    public ProvincesDTO execute() {
        // obtenemos la lista distinta y la envolvemos en el DTO
        List<String> provinces = addressRepository.findDistinctProvinces();
        return new ProvincesDTO(provinces);
    }
}

