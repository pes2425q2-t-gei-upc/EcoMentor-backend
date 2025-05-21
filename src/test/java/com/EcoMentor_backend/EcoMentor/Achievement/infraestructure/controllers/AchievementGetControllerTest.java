package com.EcoMentor_backend.EcoMentor.Achievement.infraestructure.controllers;

import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.controllers.AchievementGetController;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.GetAllAchievementsUseCase;
import com.EcoMentor_backend.EcoMentor.Achievement.useCases.dto.AchievementDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUserIdByToken;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AchievementGetControllerTest {

    private GetAllAchievementsUseCase getAllAchievementsUseCase;
    private GetUserIdByToken getUserIdByToken;
    private AchievementGetController controller;

    @BeforeEach
    public void setUp() {
        getAllAchievementsUseCase = mock(GetAllAchievementsUseCase.class);
        getUserIdByToken = mock(GetUserIdByToken.class);
        controller = new AchievementGetController(getAllAchievementsUseCase, getUserIdByToken);
    }

    @Test
    public void testGetAllAchievements_ReturnsAchievementsForUser() {
        // Arrange
        String token = "fake-jwt-token";
        Long userId = 42L;
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);

        when(getUserIdByToken.execute(token)).thenReturn(userId);

        List<AchievementDTO> mockAchievements = List.of(
                new AchievementDTO("1", 1, 1),
                new AchievementDTO("2", 1, 1)
        );
        when(getAllAchievementsUseCase.execute(userId)).thenReturn(mockAchievements);

        // Act
        ResponseEntity<List<AchievementDTO>> response = controller.getAllAchievements(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockAchievements, response.getBody());

        verify(getUserIdByToken, times(1)).execute(token);
        verify(getAllAchievementsUseCase, times(1)).execute(userId);
    }

    @Test
    public void testGetAllAchievements_InvalidHeader_ReturnsNullToken() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        when(getUserIdByToken.execute(null)).thenReturn(99L); // simulate fallback

        when(getAllAchievementsUseCase.execute(99L)).thenReturn(List.of());

        // Act
        ResponseEntity<List<AchievementDTO>> response = controller.getAllAchievements(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(getUserIdByToken).execute(null);
        verify(getAllAchievementsUseCase).execute(99L);
    }

    @Test
    public void testGetAllAchievements_HeaderWithoutBearer_ReturnsNullToken() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("SomeOtherFormat token");

        when(getUserIdByToken.execute(null)).thenReturn(88L);
        when(getAllAchievementsUseCase.execute(88L)).thenReturn(List.of());

        // Act
        ResponseEntity<List<AchievementDTO>> response = controller.getAllAchievements(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(getUserIdByToken).execute(null);
        verify(getAllAchievementsUseCase).execute(88L);
    }
}
