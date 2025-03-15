package com.EcoMentor_backend.EcoMentor.TestAddress.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.controllers.AddressDeleteController;
import com.EcoMentor_backend.EcoMentor.Address.useCases.DeleteAddressUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class AddressDeleteControllerTest {

    @Mock
    private DeleteAddressUseCase deleteAddressUseCase;

    @InjectMocks
    private AddressDeleteController addressDeleteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteAddress() {
        Long addressId = 1L;

        doNothing().when(deleteAddressUseCase).execute(addressId);

        ResponseEntity<Void> response = addressDeleteController.deleteAddress(addressId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteAddressUseCase).execute(addressId);
    }
}