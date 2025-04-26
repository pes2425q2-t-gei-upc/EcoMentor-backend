package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.GetCertificateByCertificateIdWFEUseCase;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.CreateChatWithCertificateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class PostContextUseCaseTest {

private ChatUseCase chatUseCase;
private GetCertificateByCertificateIdWFEUseCase getCertificateByCertificateIdWFEUseCase;
private PostContextUseCase postContextUseCase;

@BeforeEach
void setUp() {
    chatUseCase = mock(ChatUseCase.class);
    getCertificateByCertificateIdWFEUseCase = mock(GetCertificateByCertificateIdWFEUseCase.class);
    postContextUseCase = new PostContextUseCase(chatUseCase, getCertificateByCertificateIdWFEUseCase);
}




@Test
@DisplayName("Throws exception when certificate retrieval fails")
void throwsExceptionWhenCertificateRetrievalFails() {
    CreateChatWithCertificateDTO dto = new CreateChatWithCertificateDTO(1L, "Chat1", LocalDateTime.now(), "Message", 123L);
    when(getCertificateByCertificateIdWFEUseCase.execute(123L)).thenThrow(new RuntimeException("Certificate not found"));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> postContextUseCase.execute(dto));

    assertEquals("Certificate not found", exception.getMessage());
}


}