package com.EcoMentor_backend.EcoMentor.Role.infrastructure.controllers;

import com.EcoMentor_backend.EcoMentor.Role.useCases.GetAllRolesUseCase;
import com.EcoMentor_backend.EcoMentor.Role.useCases.GetRoleUseCase;
import com.EcoMentor_backend.EcoMentor.Role.useCases.dto.RoleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/roles")
public class RoleGetController {
    private final GetAllRolesUseCase getAllRolesUseCase;

    private final GetRoleUseCase getRoleUseCase;

    public RoleGetController( GetAllRolesUseCase getAllRolesUseCase, GetRoleUseCase getRoleUseCase) {
        this.getAllRolesUseCase = getAllRolesUseCase;
        this.getRoleUseCase = getRoleUseCase;
    }

    @GetMapping
    @PreAuthorize("(hasRole('ROLE_ADMIN'))")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = getAllRolesUseCase.execute();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @PreAuthorize("(hasRole('ROLE_ADMIN'))")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        RoleDTO role = getRoleUseCase.execute(id);
        return ResponseEntity.ok(role);
    }
}
