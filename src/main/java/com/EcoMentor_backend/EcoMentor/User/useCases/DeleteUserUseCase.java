package com.EcoMentor_backend.EcoMentor.User.useCases;

import com.EcoMentor_backend.EcoMentor.Chat.useCases.DeleteAllChatsUseCase;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UserDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class DeleteUserUseCase {
    private final UserRepository userRepository;
    private final DeleteAllChatsUseCase deleteAllChatsUseCase;

    public DeleteUserUseCase(UserRepository userRepository, DeleteAllChatsUseCase deleteAllChatsUseCase) {
        this.userRepository = userRepository;
        this.deleteAllChatsUseCase = deleteAllChatsUseCase;
    }

    public void execute(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User not found"));
        userRepository.delete(user);
        deleteAllChatsUseCase.execute(id);
    }

}
