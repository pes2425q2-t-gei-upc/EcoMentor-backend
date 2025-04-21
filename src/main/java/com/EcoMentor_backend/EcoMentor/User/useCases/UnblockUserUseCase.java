package com.EcoMentor_backend.EcoMentor.User.useCases;

import com.EcoMentor_backend.EcoMentor.User.entity.Role;
import com.EcoMentor_backend.EcoMentor.User.entity.RoleName;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.RoleRepository;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class UnblockUserUseCase {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void execute(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User not found"));
        Role blockedRole = roleRepository.findByName(RoleName.ROLE_BLOCKED).orElseThrow();
        if (user.getRoles().contains(blockedRole)) {
            user.getRoles().remove(blockedRole);
            userRepository.save(user);
        }
    }
}
