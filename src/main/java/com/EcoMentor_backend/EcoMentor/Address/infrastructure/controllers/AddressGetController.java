package com.EcoMentor_backend.EcoMentor.Address.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Address.useCases.*;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/address")
public class AddressGetController {
    private final GetAddressByAddressIdUseCase getAddressByAddressIdUseCase;
    private final GetAllAddressUseCase getAllAddressUseCase;
    private final GetNearAddressUseCase getNearAddressUseCase;
    private final GetAddressByProvinceUseCase getAddressByProvinceUseCase;
    private final GetAddressByTownUseCase getAddressByTownUseCase;


    public AddressGetController(GetAddressByAddressIdUseCase getAddressByAddressIdUseCase, GetAllAddressUseCase getAllAddressUseCase, GetAddressByProvinceUseCase getAddressByProvinceUseCase,
                                GetNearAddressUseCase getNearAddressUseCase, GetAddressByTownUseCase getAddressByTownUseCase) {
        this.getAddressByAddressIdUseCase = getAddressByAddressIdUseCase;
        this.getAddressByProvinceUseCase = getAddressByProvinceUseCase;
        this.getAllAddressUseCase = getAllAddressUseCase;
        this.getNearAddressUseCase = getNearAddressUseCase;
        this.getAddressByTownUseCase = getAddressByTownUseCase;
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAllAddress() {
        List<AddressDTO> address = getAllAddressUseCase.execute();
        return ResponseEntity.ok(address);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long addressId) {
        AddressDTO address = getAddressByAddressIdUseCase.execute(addressId);
        if (address == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address);
    }
    @GetMapping("/near")
    public ResponseEntity<List<AddressDTO>> getNearAddress(@RequestParam double radius, @RequestParam double latitude, @RequestParam double longitude) {
        List<AddressDTO> address = getNearAddressUseCase.execute(radius, latitude, longitude);
        if (address.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address);
    }

    @GetMapping("/province/{province}")
    public ResponseEntity<List<AddressDTO>> getAddressByProvince(@PathVariable String province) {
        List<AddressDTO> address = getAddressByProvinceUseCase.execute(province);
        if (address.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address);
    }

    @GetMapping("/town/{town}")
    public ResponseEntity<List<AddressDTO>> getAddressByTown(@PathVariable String town) {
        List<AddressDTO> address = getAddressByTownUseCase.execute(town);
        if (address.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address);
    }

}
