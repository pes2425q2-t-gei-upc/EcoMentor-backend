package com.EcoMentor_backend.EcoMentor.User.useCases;

import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class IncreaseWarningUseCase {
    private final UserRepository userRepository;

    public int execute(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        int newWarnings = user.getWarnings() + 1;
        user.setWarnings(newWarnings);
        userRepository.save(user);
        //if (newWarnings%5 == 0) {
        //todo send email
        //}
        return newWarnings;
    }
}
