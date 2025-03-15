package com.EcoMentor_backend.EcoMentor.User.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetAllUsersUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetByEmailUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUserByIdUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserGetController {
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetByEmailUseCase getByEmailUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;

    public UserGetController(GetAllUsersUseCase getAllUsersUseCase, GetByEmailUseCase getByEmailUseCase, GetUserByIdUseCase getUserByIdUseCase) {
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getByEmailUseCase = getByEmailUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return getAllUsersUseCase.execute();
    }

    @GetMapping("/email/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        return getByEmailUseCase.execute(email);
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable long id) {
        return getUserByIdUseCase.execute(id);
    }
}
