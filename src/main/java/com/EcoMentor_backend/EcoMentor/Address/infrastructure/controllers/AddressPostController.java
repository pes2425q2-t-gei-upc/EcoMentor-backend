package com.EcoMentor_backend.EcoMentor.Address.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Address.useCases.CreateAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.CreateAddressDTO;
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

    public AddressPostController(CreateAddressUseCase createAddressUseCase) {
        this.createAddressUseCase = createAddressUseCase;
    }
    @PostMapping
    public ResponseEntity<Long> createAddress(@RequestBody @Validated CreateAddressDTO createAddressDTO) {
        Long id = createAddressUseCase.execute(createAddressDTO);

        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}
