package com.EcoMentor_backend.EcoMentor.Role.useCases;

import com.EcoMentor_backend.EcoMentor.Role.entity.Role;
import com.EcoMentor_backend.EcoMentor.Role.infrastructure.repositories.RoleRepository;
import com.EcoMentor_backend.EcoMentor.Role.useCases.dto.RoleDTO;
import com.EcoMentor_backend.EcoMentor.Role.useCases.mapper.RoleMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class GetAllRolesUseCase {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public GetAllRolesUseCase(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public List<RoleDTO> execute() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toDTO).collect(Collectors.toList());
    }
}
