package com.EcoMentor_backend.EcoMentor.Address.useCases.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTOSimple {
    private Long addressId;
    private String addressName;
    private String addressNumber;
    private Integer zipcode;
    private String town;
    private String region;
    private String province;
    private float longitude;
    private float latitude;
    private List<Long> certificates;
}
