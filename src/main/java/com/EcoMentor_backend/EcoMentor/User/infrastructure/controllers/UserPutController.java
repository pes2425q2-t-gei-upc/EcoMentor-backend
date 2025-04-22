package com.EcoMentor_backend.EcoMentor.User.infrastructure.controllers;


import com.EcoMentor_backend.EcoMentor.User.useCases.BlockUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.UnblockUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.UpdateSelfUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.UpdateUserUseCase;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UpdateUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserPutController {

    private final UpdateUserUseCase updateUserUseCase;
    private final UpdateSelfUseCase updateSelfUseCase;
    private final BlockUserUseCase blockUserUseCase;
    private final UnblockUserUseCase unblockUserUseCase;

    @PutMapping("/me")
    public ResponseEntity<Void> updateSelf(@RequestBody @Validated UpdateUserDTO user,
                                           @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new BadCredentialsException("Invalid token");
        }
        String token = authorizationHeader.substring(7);

        updateSelfUseCase.execute(user, token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("(hasRole('ROLE_ADMIN'))")
    public ResponseEntity<Void> updateUser(@RequestBody @Validated UpdateUserDTO user, @PathVariable Long id) {
        updateUserUseCase.execute(id, user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}/block")
    @PreAuthorize("(hasRole('ROLE_ADMIN'))")
    public ResponseEntity<Void> blockUser(@PathVariable Long id) {
        blockUserUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}/unblock")
    @PreAuthorize("(hasRole('ROLE_ADMIN'))")
    public ResponseEntity<Void> unblockUser(@PathVariable Long id) {
        unblockUserUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
