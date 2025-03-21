package com.EcoMentor_backend.EcoMentor.Address.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.OfficialCertificateWFE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTOWithOfficialCertificate {
    private Long addressId;
    private String addressName;
    private String addressNumber;
    private Integer zipcode;
    private String town;
    private String region;
    private String province;
    private float longitude;
    private float latitude;
    private List<OfficialCertificateWFE> certificates;
}
