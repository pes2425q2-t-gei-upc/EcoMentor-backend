package com.EcoMentor_backend.EcoMentor.TestAddress.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.controllers.AddressGetController;
import com.EcoMentor_backend.EcoMentor.Address.useCases.*;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AddressGetControllerTest {

    @Mock
    private GetAddressByAddressIdUseCase getAddressByAddressIdUseCase;

    @Mock
    private GetAllAddressUseCase getAllAddressUseCase;

    @Mock
    private GetNearAddressUseCase getNearAddressUseCase;

    @Mock
    private GetAddressByProvinceUseCase getAddressByProvinceUseCase;

    @Mock
    private GetAddressByTownUseCase getAddressByTownUseCase;

    @InjectMocks
    private AddressGetController addressGetController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAddressWithPagination() {
        int page = 0;
        int size = 10;
        String sort = "addressId";

        Page<AddressDTO> addressPage = mock(Page.class);

        when(getAllAddressUseCase.execute(page, size, sort)).thenReturn(addressPage);

        ResponseEntity<Page<AddressDTO>> response = addressGetController.getAllAddress(page, size, sort);

        assertEquals(ResponseEntity.ok(addressPage), response);
        verify(getAllAddressUseCase).execute(page, size, sort);
    }

    @Test
    public void testGetAddress() {
        Long addressId = 1L;
        AddressDTO addressDTO = new AddressDTO();

        when(getAddressByAddressIdUseCase.execute(addressId)).thenReturn(addressDTO);

        ResponseEntity<AddressDTO> response = addressGetController.getAddress(addressId);

        assertEquals(ResponseEntity.ok(addressDTO), response);
        verify(getAddressByAddressIdUseCase).execute(addressId);
    }

    @Test
    public void testGetNearAddress() {
        double radius = 10.0;
        double latitude = 40.0;
        double longitude = -3.0;
        AddressDTO addressDTO = new AddressDTO();
        List<AddressDTO> addressList = Collections.singletonList(addressDTO);

        when(getNearAddressUseCase.execute(radius, latitude, longitude)).thenReturn(addressList);

        ResponseEntity<List<AddressDTO>> response = addressGetController.getNearAddress(radius, latitude, longitude);

        assertEquals(ResponseEntity.ok(addressList), response);
        verify(getNearAddressUseCase).execute(radius, latitude, longitude);
    }

    @Test
    public void testGetAddressByProvince() {
        String province = "Sample Province";
        AddressDTO addressDTO = new AddressDTO();
        List<AddressDTO> addressList = Collections.singletonList(addressDTO);

        when(getAddressByProvinceUseCase.execute(province)).thenReturn(addressList);

        ResponseEntity<List<AddressDTO>> response = addressGetController.getAddressByProvince(province);

        assertEquals(ResponseEntity.ok(addressList), response);
        verify(getAddressByProvinceUseCase).execute(province);
    }

    @Test
    public void testGetAddressByTown() {
        String town = "Sample Town";
        AddressDTO addressDTO = new AddressDTO();
        List<AddressDTO> addressList = Collections.singletonList(addressDTO);

        when(getAddressByTownUseCase.execute(town)).thenReturn(addressList);

        ResponseEntity<List<AddressDTO>> response = addressGetController.getAddressByTown(town);

        assertEquals(ResponseEntity.ok(addressList), response);
        verify(getAddressByTownUseCase).execute(town);
    }
}