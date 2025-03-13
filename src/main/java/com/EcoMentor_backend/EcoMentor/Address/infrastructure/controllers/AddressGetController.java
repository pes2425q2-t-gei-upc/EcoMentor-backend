package com.EcoMentor_backend.EcoMentor.Address.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Address.useCases.*;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/address")
public class AddressGetController {
    private final GetAddressByAddressIdUserCase getAddressByAddressIdUserCase;
    private final GetAllAddressUserCase getAllAddressUserCase;
    private final GetNearAddressUserCase getNearAddressUserCase;
    private final GetAddressByProvince getAddressByProvince;
    private final GetAddressByTownUserCase getAddressByTownUserCase;


    public AddressGetController(GetAddressByAddressIdUserCase getAddressByAddressIdUserCase, GetAllAddressUserCase getAllAddressUserCase, GetAddressByProvince getAddressByProvince,
                                GetNearAddressUserCase getNearAddressUserCase, GetAddressByTownUserCase getAddressByTownUserCase) {
        this.getAddressByAddressIdUserCase = getAddressByAddressIdUserCase;
        this.getAddressByProvince = getAddressByProvince;
        this.getAllAddressUserCase = getAllAddressUserCase;
        this.getNearAddressUserCase = getNearAddressUserCase;
        this.getAddressByTownUserCase = getAddressByTownUserCase;
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAllAddress() {
        List<AddressDTO> address = getAllAddressUserCase.execute();
        return ResponseEntity.ok(address);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDTO> getAddress(Long addressId) {
        AddressDTO address = getAddressByAddressIdUserCase.execute(addressId);
        return ResponseEntity.ok(address);
    }
    @GetMapping("/near")
    public ResponseEntity<List<AddressDTO>> getNearAddress(@RequestParam double radius, @RequestParam double latitude, @RequestParam double longitude) {
        List<AddressDTO> address = getNearAddressUserCase.execute(radius, latitude, longitude);
        return ResponseEntity.ok(address);
    }

    @GetMapping("/province/{province}")
    public ResponseEntity<List<AddressDTO>> getAddressByProvince(String province) {
        List<AddressDTO> address = getAddressByProvince.execute(province);
        return ResponseEntity.ok(address);
    }

    @GetMapping("/town/{town}")
    public ResponseEntity<List<AddressDTO>> getAddressByTown(String town) {
        List<AddressDTO> address = getAddressByTownUserCase.execute(town);
        return ResponseEntity.ok(address);
    }

}
