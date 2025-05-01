package com.EcoMentor_backend.EcoMentor.TestAddress.infraestructure.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.EcoMentor_backend.EcoMentor.Address.infrastructure.controllers.AddressGetController;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByAddressIdUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByBestQualificationUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByBoundingBoxUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByProvinceUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByTownUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAllAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAverageValuesInAZonUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetFilterAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetGraphValuesEmissionsUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetGraphValuesEnergyUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetGraphValuesPerformanceUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetGraphValuesQualificationUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetGraphValuesRenewableUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetMultipleFiltersAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetNearAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOBestQualification;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOSimple;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AverageValuesDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.FilterRequestDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.GraphicDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.GraphicDTOQualification;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;





public class AddressGetControllerTest {


    @Mock private GetAddressByAddressIdUseCase getAddressByAddressIdUseCase;
    @Mock private GetAllAddressUseCase getAllAddressUseCase;
    @Mock private GetNearAddressUseCase getNearAddressUseCase;
    @Mock private GetAddressByProvinceUseCase getAddressByProvinceUseCase;
    @Mock private GetAddressByTownUseCase getAddressByTownUseCase;
    @Mock private GetAddressByBoundingBoxUseCase getAddressByBoundingBoxUseCase;
    @Mock private GetFilterAddressUseCase getFilterAddressUseCase;
    @Mock private GetAddressByBestQualificationUseCase getAddressByBestQualification;
    @Mock private GetAverageValuesInAZonUseCase getAverageValuesInAZonUseCase;
    @Mock private GetGraphValuesPerformanceUseCase getGraphValuesPerformanceUseCase;
    @Mock private GetGraphValuesEnergyUseCase getGraphValuesEnergyUseCase;
    @Mock private GetGraphValuesEmissionsUseCase getGraphValuesEmissionsUseCase;
    @Mock private GetGraphValuesQualificationUseCase getGraphValuesQualificationUseCase;
    @Mock private GetGraphValuesRenewableUseCase getGraphValuesRenewableUseCase;
    @Mock private GetMultipleFiltersAddressUseCase getMultipleFiltersAddressUseCase;

    @InjectMocks
    private AddressGetController addressGetController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAddressWithPagination() {
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
    void testGetAddress() {
        Long addressId = 1L;
        AddressDTO addressDTO = new AddressDTO();

        when(getAddressByAddressIdUseCase.execute(addressId)).thenReturn(addressDTO);

        ResponseEntity<AddressDTO> response = addressGetController.getAddress(addressId);

        assertEquals(ResponseEntity.ok(addressDTO), response);
        verify(getAddressByAddressIdUseCase).execute(addressId);
    }

    @Test
    void testGetNearAddress() {
        double radius = 10.0;
        double latitude = 40.0;
        double longitude = -3.0;
        List<AddressDTO> addressList = Collections.singletonList(new AddressDTO());

        when(getNearAddressUseCase.execute(radius, latitude, longitude)).thenReturn(addressList);

        ResponseEntity<List<AddressDTO>> response = addressGetController.getNearAddress(radius, latitude, longitude);

        assertEquals(ResponseEntity.ok(addressList), response);
        verify(getNearAddressUseCase).execute(radius, latitude, longitude);
    }

    @Test
    void testGetAddressByProvince() {
        String province = "Sample Province";
        List<AddressDTO> addressList = Collections.singletonList(new AddressDTO());

        when(getAddressByProvinceUseCase.execute(province)).thenReturn(addressList);

        ResponseEntity<List<AddressDTO>> response = addressGetController.getAddressByProvince(province);

        assertEquals(ResponseEntity.ok(addressList), response);
        verify(getAddressByProvinceUseCase).execute(province);
    }

    @Test
    void testGetAddressByTown() {
        String town = "Sample Town";
        List<AddressDTO> addressList = Collections.singletonList(new AddressDTO());

        when(getAddressByTownUseCase.execute(town)).thenReturn(addressList);

        ResponseEntity<List<AddressDTO>> response = addressGetController.getAddressByTown(town);

        assertEquals(ResponseEntity.ok(addressList), response);
        verify(getAddressByTownUseCase).execute(town);
    }

    @Test
    void testGetAddressByBoundingBox() {
        double minLat = 40.0;
        double maxLat = 41.0;
        double minLng = -3.0;
        double maxLng = -2.0;
        List<AddressDTOSimple> addressList = Collections.singletonList(new AddressDTOSimple());

        when(getAddressByBoundingBoxUseCase.execute(minLat, maxLat, minLng, maxLng)).thenReturn(addressList);

        ResponseEntity<List<AddressDTOSimple>> response = addressGetController
                .getOfficialCertificatesByAddressBoundingBox(minLat, maxLat, minLng, maxLng);

        assertEquals(ResponseEntity.ok(addressList), response);
        verify(getAddressByBoundingBoxUseCase).execute(minLat, maxLat, minLng, maxLng);
    }

    @Test
    void testGetAddressByFilter() {
        String parameter = "town";
        String value = "Sample Town";
        double minLat = 40.0;
        double maxLat = 41.0;
        double minLng = -3.0;
        double maxLng = -2.0;
        List<AddressDTOSimple> addressList = Collections.singletonList(new AddressDTOSimple());

        when(getFilterAddressUseCase.execute(parameter, value, minLat, maxLat, minLng, maxLng)).thenReturn(addressList);

        ResponseEntity<List<AddressDTOSimple>> response = addressGetController.getAddressByFilter(
                parameter, value, minLat, maxLat, minLng, maxLng);

        assertEquals(ResponseEntity.ok(addressList), response);
        verify(getFilterAddressUseCase).execute(parameter, value, minLat, maxLat, minLng, maxLng);
    }

    @Test
    void testGetAddressByBestQualification() {
        String town = "Sample Town";
        Integer zipcode = 12345;
        Double latitude = 40.0;
        Double longitude = -3.0;
        Integer radius = 5;
        List<AddressDTOBestQualification> addressList = Collections.singletonList(new AddressDTOBestQualification());

        when(getAddressByBestQualification.execute(town, zipcode, latitude, longitude, radius))
                .thenReturn(addressList);

        ResponseEntity<List<AddressDTOBestQualification>> response = addressGetController.getAddressByBestQualification(
                town, zipcode, latitude, longitude, radius);

        assertEquals(ResponseEntity.ok(addressList), response);
        verify(getAddressByBestQualification).execute(town, zipcode, latitude, longitude, radius);
    }

    @Test
    void testGetAverageValuesInAZon() {
        Double latitude = 40.0;
        Double longitude = -3.0;
        Integer radius = 5;
        AverageValuesDTO averageValuesDTO = new AverageValuesDTO();

        when(getAverageValuesInAZonUseCase.execute(latitude, longitude, radius)).thenReturn(averageValuesDTO);

        ResponseEntity<AverageValuesDTO> response = addressGetController
                .getAverageValuesInAZon(latitude, longitude, radius);

        assertEquals(ResponseEntity.ok(averageValuesDTO), response);
        verify(getAverageValuesInAZonUseCase).execute(latitude, longitude, radius);
    }

    @Test
    void testGetGraphValuesPerformance() {
        List<GraphicDTO> graphics = Collections.singletonList(new GraphicDTO());

        when(getGraphValuesPerformanceUseCase.execute(null, null, null)).thenReturn(graphics);

        ResponseEntity<List<GraphicDTO>> response = addressGetController.getGraphValuesPerformance(null, null, null);

        assertEquals(ResponseEntity.ok(graphics), response);
        verify(getGraphValuesPerformanceUseCase).execute(null, null, null);
    }

    @Test
    void testGetGraphValuesEnergy() {
        List<GraphicDTO> graphics = Collections.singletonList(new GraphicDTO());

        when(getGraphValuesEnergyUseCase.execute(null, null, null)).thenReturn(graphics);

        ResponseEntity<List<GraphicDTO>> response = addressGetController.getGraphValuesEnergy(null, null, null);

        assertEquals(ResponseEntity.ok(graphics), response);
        verify(getGraphValuesEnergyUseCase).execute(null, null, null);
    }

    @Test
    void testGetGraphValuesEmissions() {
        List<GraphicDTO> graphics = Collections.singletonList(new GraphicDTO());

        when(getGraphValuesEmissionsUseCase.execute(null, null, null)).thenReturn(graphics);

        ResponseEntity<List<GraphicDTO>> response = addressGetController.getGraphValuesEmissions(null, null, null);

        assertEquals(ResponseEntity.ok(graphics), response);
        verify(getGraphValuesEmissionsUseCase).execute(null, null, null);
    }

    @Test
    void testGetGraphValuesQualification() {
        List<GraphicDTOQualification> graphics = Collections.singletonList(new GraphicDTOQualification());

        when(getGraphValuesQualificationUseCase.execute(null, null, null)).thenReturn(graphics);

        ResponseEntity<List<GraphicDTOQualification>> response = addressGetController
                .getGraphValuesQualification(null, null, null);

        assertEquals(ResponseEntity.ok(graphics), response);
        verify(getGraphValuesQualificationUseCase).execute(null, null, null);
    }

    @Test
    void testGetGraphValuesRenewable() {
        List<GraphicDTO> graphics = Collections.singletonList(new GraphicDTO());

        when(getGraphValuesRenewableUseCase.execute(null, null, null)).thenReturn(graphics);

        ResponseEntity<List<GraphicDTO>> response = addressGetController.getGraphValuesRenewable(null, null, null);

        assertEquals(ResponseEntity.ok(graphics), response);
        verify(getGraphValuesRenewableUseCase).execute(null, null, null);
    }


}
