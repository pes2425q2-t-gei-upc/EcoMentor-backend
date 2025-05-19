package com.EcoMentor_backend.EcoMentor.Chat.useCases;


import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByCertificateIdWFEUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.CreateChatWithCertificateDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PostContextUseCase {
    private ChatUseCase chatUseCase;
    private GetCertificateByCertificateIdWFEUseCase getCertificateByCertificateIdWFEUseCase;

    public ChatResponseDTO execute(CreateChatWithCertificateDTO createChatWithCertificateDTO, Long userId) {
        if (createChatWithCertificateDTO.getCertificateId() != null) {
            CertificateWithoutForeignEntitiesDTO dto = getCertificateByCertificateIdWFEUseCase.execute(
                   createChatWithCertificateDTO.getCertificateId());

            String message = createChatWithCertificateDTO.getMessage()
                    + " " + dto.toString();

            return chatUseCase.execute(message, userId,
                    createChatWithCertificateDTO.getChatName());


        } else {
            return chatUseCase.execute(createChatWithCertificateDTO.getMessage(),
                    userId, createChatWithCertificateDTO.getChatName());

        }

    }

}
