package com.EcoMentor_backend.EcoMentor.Address.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Address.useCases.DeleteAddressUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/address")
public class AddressDeleteController {
    private final DeleteAddressUseCase deleteAddressUseCase;

    public AddressDeleteController(DeleteAddressUseCase deleteAddressUseCase) {
        this.deleteAddressUseCase = deleteAddressUseCase;
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        deleteAddressUseCase.execute(addressId);
        return ResponseEntity.noContent().build();
    }
}
