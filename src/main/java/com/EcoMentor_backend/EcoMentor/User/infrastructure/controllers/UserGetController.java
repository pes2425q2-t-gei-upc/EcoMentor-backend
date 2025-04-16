package com.EcoMentor_backend.EcoMentor.User.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetAllUsersUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetByEmailUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetSelfUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUserByIdUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.GetUsersCertificatesUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UserDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserGetController {
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetByEmailUseCase getByEmailUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final GetUsersCertificatesUseCase getUsersCertificatesUseCase;
    private final GetSelfUseCase getSelfUseCase;

    public UserGetController(GetAllUsersUseCase getAllUsersUseCase, GetByEmailUseCase getByEmailUseCase,
                             GetUserByIdUseCase getUserByIdUseCase,
                             GetUsersCertificatesUseCase getUsersCertificatesUseCase,
                             GetSelfUseCase getSelfUseCase) {
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getByEmailUseCase = getByEmailUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.getUsersCertificatesUseCase = getUsersCertificatesUseCase;
        this.getSelfUseCase = getSelfUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "name") String sort) {

        Page<UserDTO> users = getAllUsersUseCase.execute(page, size, sort);
        return ResponseEntity.ok(users);
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

    @GetMapping("/me")
    public UserDTO getMe(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new BadCredentialsException("Invalid token");
        }
        String token = authorizationHeader.substring(7);
        return getSelfUseCase.execute(token);
    }
}
