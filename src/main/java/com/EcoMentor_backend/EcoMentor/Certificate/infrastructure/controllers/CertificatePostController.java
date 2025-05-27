package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.CalculateUnofficialCertificateUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.CreateCertificateUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateBySetOfValuesUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculateUnofficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CalculatorResultsDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CreateCertificateDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUserIdByToken;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/certificate")
public class CertificatePostController {
    private final CreateCertificateUseCase createCertificateUseCase;
    private final GetCertificateBySetOfValuesUseCase getCertificateBySetOfValuesUseCase;
    private final CalculateUnofficialCertificateUseCase calculateUnofficialCertificateUseCase;
    private GetUserIdByToken getUserIdByToken;


    @PostMapping
    public ResponseEntity<Void> createCertificate(@RequestBody @Validated CreateCertificateDTO certificate) {
        createCertificateUseCase.execute(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/setOfValues")
    public ResponseEntity<List<CertificateWithoutForeignEntitiesDTO>> getCertificateBySetOfValues(
            @RequestParam String parameter, @RequestBody List<Object> values) {
        List<CertificateWithoutForeignEntitiesDTO> certificates = getCertificateBySetOfValuesUseCase.execute(parameter,
                values);
        if (certificates.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(certificates);
        }
    }

    @PostMapping("/calculateUnofficialCertificate")
    public ResponseEntity<CalculatorResultsDTO> calculateUnofficialCertificate(
            @RequestBody @Validated CalculateUnofficialCertificateDTO calculateUnofficialCertificateDTO,
            HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        CalculatorResultsDTO calculatorResultsDTO = calculateUnofficialCertificateUseCase
                .execute(calculateUnofficialCertificateDTO, userId);
        if (calculatorResultsDTO == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(calculatorResultsDTO);
        }
    }

    private Long getUserIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        return getUserIdByToken.execute(token);
    }

}