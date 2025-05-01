package com.EcoMentor_backend.EcoMentor.Shared;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private Environment env;

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmail_SendsPlainTextEmailSuccessfully() {
        // Arrange
        when(env.getProperty("spring.mail.username")).thenReturn("test@example.com");

        // Act
        emailService.sendEmail("recipient@example.com", "Subject", "Text body");

        // Assert
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendHtmlEmail_SendsHtmlEmailSuccessfully() throws MessagingException, IOException {
        // Arrange
        String htmlPath = "templates/email.html";
        String htmlContent = "<html><body>Hello!</body></html>";
        Resource resource = mock(Resource.class);
        InputStream inputStream = new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8));

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(resourceLoader.getResource("classpath:" + htmlPath)).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(inputStream);

        // Act
        emailService.sendHtmlEmail("recipient@example.com", "HTML Subject", htmlPath);

        // Assert
        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    void testLoadHtmlTemplate_ThrowsIOException_WhenFileNotFound() throws IOException {
        // Arrange
        String invalidPath = "nonexistent.html";
        when(resourceLoader.getResource("classpath:" + invalidPath)).thenReturn(mock(Resource.class));
        when(resourceLoader.getResource("classpath:" + invalidPath).getInputStream()).thenThrow(new IOException("File not found"));

        // Act & Assert
        assertThatThrownBy(() -> emailService.loadHtmlTemplate(invalidPath))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("File not found");
    }
}
