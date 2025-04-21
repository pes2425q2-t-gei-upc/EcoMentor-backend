package com.EcoMentor_backend.EcoMentor.Chat.useCases.dto;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.OfficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.OfficialCertificateWFEDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.UnofficialCertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.UnofficialCertificateWFEDTO;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateChatWithCertificateDTO {

    @NotBlank
    private Long userId;

    @NotBlank
    private String chatName;

    @NotBlank
    LocalDateTime dateTime;

    @NotBlank
    private String message;

    private Long certificateId;

}
