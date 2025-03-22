package com.EcoMentor_backend.EcoMentor.User.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetAllUsersUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetByEmailUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUserByIdUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUsersCertificatesUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UserDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserGetController {
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetByEmailUseCase getByEmailUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final GetUsersCertificatesUseCase getUsersCertificatesUseCase;

    public UserGetController(GetAllUsersUseCase getAllUsersUseCase, GetByEmailUseCase getByEmailUseCase,
                             GetUserByIdUseCase getUserByIdUseCase,
                             GetUsersCertificatesUseCase getUsersCertificatesUseCase) {
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getByEmailUseCase = getByEmailUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.getUsersCertificatesUseCase = getUsersCertificatesUseCase;
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

    @GetMapping("/{id}/certificates")
    public List<CertificateDTO> getUsersCertificates(@PathVariable Long id) {
        return getUsersCertificatesUseCase.execute(id);
    }
}
