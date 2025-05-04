package com.EcoMentor_backend.EcoMentor.Role.useCases;

import com.EcoMentor_backend.EcoMentor.Role.entity.Role;
import com.EcoMentor_backend.EcoMentor.Role.infrastructure.repositories.RoleRepository;
import com.EcoMentor_backend.EcoMentor.Role.useCases.dto.RoleDTO;
import com.EcoMentor_backend.EcoMentor.Role.useCases.mapper.RoleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<RoleDTO> execute(int page, int size, String sort, String order) {
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Page<Role> roles = roleRepository.findAll(pageable);
        return roles.map(roleMapper::toDTO);
    }
}
