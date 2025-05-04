package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class DeleteAddressUseCase {
    private final AddressRepository addressRepository;

    public DeleteAddressUseCase(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void execute(Long addressId) {
        Address address = addressRepository.findByAddressId(addressId);
        if (address == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No addresses found");
        }
        addressRepository.delete(address);
    }
}
