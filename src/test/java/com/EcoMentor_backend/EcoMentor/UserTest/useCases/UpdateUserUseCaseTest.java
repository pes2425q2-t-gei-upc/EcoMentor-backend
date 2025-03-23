package com.EcoMentor_backend.EcoMentor.UserTest.useCases;


import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.UpdateUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UpdateUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;


    @Test
    void shouldUpdateUser(){
        String email = "test@example.com";
        String originalName = "Original Name";
        String newName = "New Name";

        User existingUser = new User();
        existingUser.setEmail(email);
        existingUser.setName(originalName);
        Long id = existingUser.getId();

        UpdateUserDTO updateUserDTO = new UpdateUserDTO(newName);
        //Mock everything before the update of the user
        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User updatedUser = updateUserUseCase.execute(id, updateUserDTO);
        assertEquals(newName, updatedUser.getName(), "User name should be updated");
    }

    @Test
    void throwsExceptionWhenUserNotFound(){
        Long invalidId = 1L;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO("new-name");
        assertThrows( RuntimeException.class, () -> updateUserUseCase.execute(invalidId, updateUserDTO));

    }
}
