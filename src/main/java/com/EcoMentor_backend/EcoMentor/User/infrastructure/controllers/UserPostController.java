package com.EcoMentor_backend.EcoMentor.User.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.User.useCases.AddCertificateToUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.AddOfficialCertificateToUserByDocumentIdUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.CreateUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.CreateUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Validated
@RequestMapping("/api/users")
public class UserPostController {
    private final CreateUserUseCase createUserUseCase;
    private final AddCertificateToUserUseCase addCertificateToUserUseCase;
    private final AddOfficialCertificateToUserByDocumentIdUseCase addOfficialCertificateToUserByDocumentIdUseCase;

    public UserPostController(CreateUserUseCase createUserUseCase,
                              AddCertificateToUserUseCase addCertificateToUserUseCase,
                              AddOfficialCertificateToUserByDocumentIdUseCase
                                      addOfficialCertificateToUserByDocumentIdUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.addCertificateToUserUseCase = addCertificateToUserUseCase;
        this.addOfficialCertificateToUserByDocumentIdUseCase = addOfficialCertificateToUserByDocumentIdUseCase;
    }

    @PostMapping
    @PreAuthorize("(hasRole('ROLE_ADMIN'))")
    public ResponseEntity<Void> createUser(@RequestBody @Validated CreateUserDTO user) {
        createUserUseCase.execute(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{userId}/certificates/{certificateId}")
    public ResponseEntity<Void> addCertificateToUser(@PathVariable Long userId, @PathVariable Long certificateId) {
        addCertificateToUserUseCase.execute(userId, certificateId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{userId}/official-certificates/{documentId}")
    public ResponseEntity<Void> addOfficialCertificateToUser(@PathVariable Long userId,
                                                             @PathVariable String documentId) {
        addOfficialCertificateToUserByDocumentIdUseCase.execute(userId, documentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
