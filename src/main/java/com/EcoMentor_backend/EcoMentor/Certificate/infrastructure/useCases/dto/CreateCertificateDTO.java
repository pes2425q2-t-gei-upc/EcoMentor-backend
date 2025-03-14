package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateCertificateDTO {
    private Long certificateId;
    private CertificateType certificateType;
    private Address address;
    private ArrayList<Recommendation> recommendations;
}
