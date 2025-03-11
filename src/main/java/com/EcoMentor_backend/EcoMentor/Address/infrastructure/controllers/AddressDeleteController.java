package com.EcoMentor_backend.EcoMentor.Address.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Address.useCases.DeleteAddressUserCase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/address")
public class AddressDeleteController {
    private final DeleteAddressUserCase deleteAddressUserCase;

    public AddressDeleteController(DeleteAddressUserCase deleteAddressUserCase) {
        this.deleteAddressUserCase = deleteAddressUserCase;
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(Long addressId) {
        deleteAddressUserCase.execute(addressId);
        return ResponseEntity.noContent().build();
    }
}
