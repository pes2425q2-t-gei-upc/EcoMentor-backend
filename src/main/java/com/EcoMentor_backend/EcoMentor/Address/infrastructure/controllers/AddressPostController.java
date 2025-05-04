package com.EcoMentor_backend.EcoMentor.Address.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Address.useCases.CreateAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetMultipleFiltersAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOSimple;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.FilterRequestDTO;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@RequestMapping("/api/address")
public class AddressPostController {
    private final CreateAddressUseCase createAddressUseCase;
    private final GetMultipleFiltersAddressUseCase getMultipleFiltersAddressUseCase;

    public AddressPostController(CreateAddressUseCase createAddressUseCase,
                                 GetMultipleFiltersAddressUseCase getMultipleFiltersAddressUseCase) {
        this.createAddressUseCase = createAddressUseCase;
        this.getMultipleFiltersAddressUseCase = getMultipleFiltersAddressUseCase;
    }

    @PostMapping
    public ResponseEntity<Long> createAddress(@RequestBody @Validated CreateAddressDTO createAddressDTO) {
        Long id = createAddressUseCase.execute(createAddressDTO);
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/multiplefilters")
    public ResponseEntity<List<AddressDTOSimple>> getMultipleFiltersAddressUseCase(@RequestBody FilterRequestDTO
                                                                                           requestBody) {

        List<AddressDTOSimple> addresses = getMultipleFiltersAddressUseCase.execute(
                requestBody.getFilters(),
                requestBody.getMinLatitude(),
                requestBody.getMaxLatitude(),
                requestBody.getMinLongitude(),
                requestBody.getMaxLongitude()
        );

        if (addresses.isEmpty()) {
            return ResponseEntity.noContent().build(); // O .notFound().build() si prefieres
        }

        return ResponseEntity.ok(addresses);
    }
}
