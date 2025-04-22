package com.EcoMentor_backend.EcoMentor.Role.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Role.useCases.DeleteRoleUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/roles")
public class RoleDeleteController {

    private final DeleteRoleUseCase deleteRoleUseCase;

    public RoleDeleteController(DeleteRoleUseCase deleteRoleUseCase) {
        this.deleteRoleUseCase = deleteRoleUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id){
        try {
            deleteRoleUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
