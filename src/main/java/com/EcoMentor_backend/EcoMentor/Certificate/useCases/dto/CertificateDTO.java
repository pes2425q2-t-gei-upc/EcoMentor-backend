package com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto;


import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOWithoutCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Recommendation.useCases.dto.RecommendationDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;




@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,
        property = "certificateType", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CreateOfficialCertificateDTO.class, name = "OFFICIAL"),
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CertificateDTO {
    private Long certificateId;
    private CertificateType certificateType;
    private AddressDTOWithoutCertificate addressDTO;
    private List<RecommendationDTO> recommendations;
}
