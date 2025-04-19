package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetAllCertificatesUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByAddressUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByCertificateIdUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByCertificateIdWFEUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByMinMaxRangeUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByParameterUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateRecommendationsUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/certificate")
public class CertificateGetController {
    private final GetAllCertificatesUseCase getAllCertificatesUseCase;
    private final GetCertificateByCertificateIdUseCase getCertificateByIdUseCase;
    private final GetCertificateByAddressUseCase getCertificateByAddressUseCase;
    private final GetCertificateByParameterUseCase getCertificateByParameterUseCase;
    private final GetCertificateByMinMaxRangeUseCase getCertificateByMinMaxRangeUseCase;
    private final GetCertificateByCertificateIdWFEUseCase getCertificateByCertificateIdWithoutUseCase;
    private final GetCertificateRecommendationsUseCase getCertificateRecommendationsUseCase;

    public CertificateGetController(GetCertificateByParameterUseCase getCertificateByParameterUseCase,
                                    GetAllCertificatesUseCase getAllCertificatesUseCase,
                                    GetCertificateByCertificateIdUseCase getCertificateByIdUseCase,
                                    GetCertificateByAddressUseCase getCertificateByAddressUseCase,
                                    GetCertificateByMinMaxRangeUseCase getCertificateByMinMaxRangeUseCase,
                                    GetCertificateByCertificateIdWFEUseCase
                                            getCertificateByCertificateIdWithoutUseCase,
                                    GetCertificateRecommendationsUseCase getCertificateRecommendationsUseCase) {
        this.getAllCertificatesUseCase = getAllCertificatesUseCase;
        this.getCertificateByIdUseCase = getCertificateByIdUseCase;
        this.getCertificateByAddressUseCase = getCertificateByAddressUseCase;
        this.getCertificateByParameterUseCase = getCertificateByParameterUseCase;
        this.getCertificateByMinMaxRangeUseCase = getCertificateByMinMaxRangeUseCase;
        this.getCertificateByCertificateIdWithoutUseCase = getCertificateByCertificateIdWithoutUseCase;
        this.getCertificateRecommendationsUseCase = getCertificateRecommendationsUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<CertificateWithoutForeignEntitiesDTO>> getAllCertificateUseCase(
                                                                    @RequestParam(defaultValue = "0") int pageNo,
                                                                    @RequestParam(defaultValue = "10") int pageSize,
                                                        @RequestParam(defaultValue = "certificateId") String sortBy) {

        Page<CertificateWithoutForeignEntitiesDTO> certificateDTOS =
                getAllCertificatesUseCase.execute(pageNo, pageSize, sortBy);

        return ResponseEntity.ok(certificateDTOS);
    }

    @GetMapping("/{certificateId}")
    public CertificateDTO getCertificateByIdUseCase(@PathVariable Long certificateId) {
        return getCertificateByIdUseCase.execute(certificateId);
    }

    @GetMapping("/address/{addressId}")
    public List<CertificateWithoutForeignEntitiesDTO> getCertificateByAddressUseCase(@PathVariable Long addressId) {
        return getCertificateByAddressUseCase.execute(addressId);
    }

    @GetMapping("/parameter")
    public List<CertificateWithoutForeignEntitiesDTO> getCertificateByParameter(@RequestParam String parameter,
                                                                                @RequestParam String value,
                                                                                @RequestParam double minLatitude,
                                                                                @RequestParam double maxLatitude,
                                                                                @RequestParam double minLongitude,
                                                                                @RequestParam double maxLongitude) {

        return getCertificateByParameterUseCase.execute(parameter, value, minLatitude, maxLatitude, minLongitude,
                maxLongitude);
    }

    @GetMapping("/range")
    public List<CertificateWithoutForeignEntitiesDTO> getCertificateByMinMaxRange(
            @RequestParam String parameter, @RequestParam String min, @RequestParam String max) {
        return getCertificateByMinMaxRangeUseCase.execute(parameter, min, max);
    }

    @GetMapping("/wfe/{certificateId}")
    public CertificateWithoutForeignEntitiesDTO getCertificateByCertificateIdWithoutUseCase(@PathVariable Long
                                                                                                        certificateId) {
        return getCertificateByCertificateIdWithoutUseCase.execute(certificateId);
    }

    @GetMapping("/{certificateId}/recommendations")
    public ResponseEntity<List<RecommendationDTO>> getCertificateRecommendations(
            @PathVariable Long certificateId) {
        List<RecommendationDTO> recommendations =
                getCertificateRecommendationsUseCase.execute(certificateId);
        return ResponseEntity.ok(recommendations);
    }

}


