package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByCertificateIdWFEUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateWithoutForeignEntitiesDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.CreateChatWithCertificateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PostContextUseCaseTest {

    private ChatUseCase chatUseCase;
    private GetCertificateByCertificateIdWFEUseCase certUseCase;
    private PostContextUseCase postContextUseCase;

    @BeforeEach
    void setUp() {
        chatUseCase = Mockito.mock(ChatUseCase.class);
        certUseCase = Mockito.mock(GetCertificateByCertificateIdWFEUseCase.class);
        postContextUseCase = new PostContextUseCase(chatUseCase, certUseCase);
    }


    @Test
    @DisplayName("Should call chatUseCase directly when certificateId is null")
    void shouldCallChatUseCaseDirectly() {
        Long userId = 3L;
        CreateChatWithCertificateDTO input = new CreateChatWithCertificateDTO();
        input.setChatName("chatB");
        input.setMessage("Hi there");
        input.setCertificateId(null);

        ChatResponseDTO expectedResponse = ChatResponseDTO.builder()
                .message("SimpleReply").build();
        when(chatUseCase.execute("Hi there", userId, "chatB")).thenReturn(expectedResponse);

        ChatResponseDTO actual = postContextUseCase.execute(input, userId);

        assertEquals(expectedResponse, actual);
        verify(certUseCase, never()).execute(any());
        verify(chatUseCase, times(1)).execute("Hi there", userId, "chatB");
    }
}