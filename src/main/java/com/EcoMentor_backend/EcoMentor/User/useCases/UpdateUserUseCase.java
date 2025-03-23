package com.EcoMentor_backend.EcoMentor.User.useCases;


import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import com.EcoMentor_backend.EcoMentor.User.useCases.dto.UpdateUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UpdateUserUseCase {
    private final UserRepository userRepository;


    public User execute(Long userId, UpdateUserDTO newUser) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        //Add new field to update if UpdateUserDTO changed.
        user.setName(newUser.getName());
        userRepository.save(user);
        System.out.println("User with " + userId + " has been updated");
        return user;
    }
}
