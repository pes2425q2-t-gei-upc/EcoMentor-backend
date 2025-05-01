package com.EcoMentor_backend.EcoMentor.Address.infrastructure.controllers;


import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByAddressIdUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByBestQualificationUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByBoundingBoxUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByProvinceUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAddressByTownUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAllAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAllProvincesUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAllRegionsUseCase;
import com.EcoMentor_backend.EcoMentor.Address.useCases.GetAllTownsUseCase;
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
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.GraphicDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.GraphicDTOQualification;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.ProvincesDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.RegionsDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.TownsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/api/address")
public class AddressGetController {
    private final GetAddressByAddressIdUseCase getAddressByAddressIdUseCase;
    private final GetAllAddressUseCase getAllAddressUseCase;
    private final GetNearAddressUseCase getNearAddressUseCase;
    private final GetAddressByProvinceUseCase getAddressByProvinceUseCase;
    private final GetAddressByTownUseCase getAddressByTownUseCase;
    private final GetAddressByBoundingBoxUseCase getAddressByBoundingBoxUseCase;
    private final GetFilterAddressUseCase getFilterAddressUseCase;
    private final GetAddressByBestQualificationUseCase getAddressByBestQualification;
    private final GetAverageValuesInAZonUseCase getAverageValuesInAZonUseCase;
    private final GetGraphValuesPerformanceUseCase getGraphValuesPerformanceUseCase;
    private final GetGraphValuesEnergyUseCase getGraphValuesEnergyUseCase;
    private final GetGraphValuesEmissionsUseCase getGraphValuesEmissionsUseCase;
    private final GetGraphValuesQualificationUseCase getGraphValuesQualificationUseCase;
    private final GetGraphValuesRenewableUseCase getGraphValuesRenewableUseCase;
    private final GetAllTownsUseCase getAllTownsUseCase;
    private final GetAllProvincesUseCase getAllProvincesUseCase;
    private final GetAllRegionsUseCase getAllRegionsUseCase;

    @GetMapping
    public ResponseEntity<Page<AddressDTO>> getAllAddress(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "addressId") String sort) {
        Page<AddressDTO> address = getAllAddressUseCase.execute(page, size, sort);
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

    @GetMapping("/distinct/towns")
    public ResponseEntity<TownsDTO> getAllTowns() {
        TownsDTO dto = getAllTownsUseCase.execute();
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/distinct/provinces")
    public ResponseEntity<ProvincesDTO> getAllProvinces() {
        ProvincesDTO dto = getAllProvincesUseCase.execute();
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/distinct/regions")
    public ResponseEntity<RegionsDTO> getAllRegions() {
        RegionsDTO dto = getAllRegionsUseCase.execute();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/graphValuesPerformance")
    public ResponseEntity<List<GraphicDTO>> getGraphValuesPerformance(@RequestParam(required = false) String town,
                                                                       @RequestParam(required = false) String region,
                                                                      @RequestParam(required = false) String province) {

        List<GraphicDTO> response = getGraphValuesPerformanceUseCase.execute(town, region, province);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/graphValuesEnergy")
    public ResponseEntity<List<GraphicDTO>> getGraphValuesEnergy(@RequestParam(required = false) String town,
                                                                  @RequestParam(required = false) String region,
                                                                  @RequestParam(required = false) String province) {
        List<GraphicDTO> response = getGraphValuesEnergyUseCase.execute(town, region, province);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/graphValuesEmissions")
    public ResponseEntity<List<GraphicDTO>> getGraphValuesEmissions(@RequestParam(required = false) String town,
                                                                      @RequestParam(required = false) String region,
                                                                      @RequestParam(required = false) String province) {
        List<GraphicDTO> response = getGraphValuesEmissionsUseCase.execute(town, region, province);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/graphValuesQualification")
    public ResponseEntity<List<GraphicDTOQualification>> getGraphValuesQualification(@RequestParam(required = false)
                                                            String town, @RequestParam(required = false) String region,
                                                                    @RequestParam(required = false) String province) {

        List<GraphicDTOQualification> response = getGraphValuesQualificationUseCase.execute(town, region, province);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/graphValuesRenewable")
    public ResponseEntity<List<GraphicDTO>> getGraphValuesRenewable(@RequestParam(required = false) String town,
                                                                      @RequestParam(required = false) String region,
                                                                      @RequestParam(required = false) String province) {
        List<GraphicDTO> response = getGraphValuesRenewableUseCase.execute(town, region, province);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/near")
    public ResponseEntity<List<AddressDTO>> getNearAddress(@RequestParam double radius,
                                                           @RequestParam double latitude,
                                                           @RequestParam double longitude) {

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

    @GetMapping("/BoundingBox")
    public ResponseEntity<List<AddressDTOSimple>> getOfficialCertificatesByAddressBoundingBox(
                                            @RequestParam double minLatitude, @RequestParam double maxLatitude,
                                            @RequestParam double minLongitude, @RequestParam double maxLongitude) {

        List<AddressDTOSimple> address = getAddressByBoundingBoxUseCase.execute(minLatitude, maxLatitude,
                                                                        minLongitude, maxLongitude);
        if (address.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(address);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<AddressDTOSimple>> getAddressByFilter(@RequestParam String parameter,
                                                               @RequestParam String value,
                                                               @RequestParam double minLatitude,
                                                               @RequestParam double maxLatitude,
                                                               @RequestParam double minLongitude,
                                                               @RequestParam double maxLongitude) {

        List<AddressDTOSimple> address = getFilterAddressUseCase.execute(parameter, value, minLatitude, maxLatitude,
                                                                    minLongitude, maxLongitude);

        if (address.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address);
    }




    @Tag(name = "Best building certifications by area", description = "Endpoints to query addresses")
    @GetMapping("/bestQualification")
    @Operation(
            summary = "Get addresses with the best qualification",
            description = "Returns a list of addresses optionally filtered by town or"
                    + " zipcode or latitude/longitude and radius."
    )
    public ResponseEntity<List<AddressDTOBestQualification>> getAddressByBestQualification(
            @Parameter(description = "City name", example = "Barcelona")
            @RequestParam(required = false) String town,

            @Parameter(description = "Zipcode", example = "08001")
            @RequestParam(required = false) Integer zipcode,

            @Parameter(description = "Latitude for geographic search", example = "41.3851")
            @RequestParam(required = false) Double latitude,

            @Parameter(description = "Length for geographic search", example = "2.1734")
            @RequestParam(required = false) Double longitude,

            @Parameter(description = "Search radius in kilometers", example = "5")
            @RequestParam(required = false) Integer radius) {



        List<AddressDTOBestQualification> address = getAddressByBestQualification.execute(town, zipcode, latitude,
                                                                                                    longitude, radius);
        if (address.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address);

    }

    @GetMapping("/averageValues")
    public ResponseEntity<AverageValuesDTO> getAverageValuesInAZon(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Integer radius) {

        AverageValuesDTO address = getAverageValuesInAZonUseCase.execute(latitude, longitude, radius);
        if (address == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address);
    }


}
