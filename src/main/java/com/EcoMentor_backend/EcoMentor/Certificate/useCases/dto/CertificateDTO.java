package com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTO;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOWithoutCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CertificateDTO {
    private Long certificateId;
    private CertificateType certificateType;
    private AddressDTOWithoutCertificate address;
    private List<RecommendationDTO> recommendations;
}
