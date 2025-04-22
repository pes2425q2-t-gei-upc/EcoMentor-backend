package com.EcoMentor_backend.EcoMentor.User.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Role.entity.RoleName;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String name;
    private long id;
    private String email;
    private String password;
    private List<CertificateDTO> certificateDTOList;
    private List<RoleName> roles;
}
