package com.EcoMentor_backend.EcoMentor.Role.infrastructure.controllers;


import com.EcoMentor_backend.EcoMentor.Role.useCases.GetAllRolesUseCase;
import com.EcoMentor_backend.EcoMentor.Role.useCases.GetRoleUseCase;
import com.EcoMentor_backend.EcoMentor.Role.useCases.dto.RoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@RequestMapping("/api/roles")
public class RoleGetController {
    private final GetAllRolesUseCase getAllRolesUseCase;

    private final GetRoleUseCase getRoleUseCase;

    public RoleGetController(GetAllRolesUseCase getAllRolesUseCase, GetRoleUseCase getRoleUseCase) {
        this.getAllRolesUseCase = getAllRolesUseCase;
        this.getRoleUseCase = getRoleUseCase;
    }

    @GetMapping
    @PreAuthorize("(hasRole('ROLE_ADMIN'))")
    public ResponseEntity<Page<RoleDTO>> getAllRoles(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "id") String sort,
                                                     @RequestParam(defaultValue = "ASC") String sortOrder) {
        Page<RoleDTO> roles = getAllRolesUseCase.execute(page, size, sort, sortOrder);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @PreAuthorize("(hasRole('ROLE_ADMIN'))")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        RoleDTO role = getRoleUseCase.execute(id);
        return ResponseEntity.ok(role);
    }
}
