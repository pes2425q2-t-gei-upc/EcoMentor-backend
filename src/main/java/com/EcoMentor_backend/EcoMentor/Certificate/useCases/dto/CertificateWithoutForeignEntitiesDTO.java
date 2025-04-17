package com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,
        property = "certificateType", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CreateOfficialCertificateDTO.class, name = "OFFICIAL"),
    @JsonSubTypes.Type(value = CreateUnofficialCertificateDTO.class, name = "UNOFFICIAL")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CertificateWithoutForeignEntitiesDTO {
    private Long certificateId;
    private CertificateType certificateType;
}
