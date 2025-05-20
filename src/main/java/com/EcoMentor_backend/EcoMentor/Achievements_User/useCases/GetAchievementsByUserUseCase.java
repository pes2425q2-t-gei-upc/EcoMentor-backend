package com.EcoMentor_backend.EcoMentor.Achievements_User.useCases;

import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.repositories.AchievementsUserRepository;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.dto.AchievementsUserDTO;
import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.mapper.AchievementsUserMapper;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class GetAchievementsByUserUseCase {

    private final UserRepository userRepository;
    private final AchievementsUserRepository achievementsUserRepository;
    private final AchievementsUserMapper achievementsUserMapper;

    public GetAchievementsByUserUseCase(UserRepository userRepository,
                                        AchievementsUserRepository achievementsUserRepository,
                                        AchievementsUserMapper achievementsUserMapper) {
        this.userRepository = userRepository;
        this.achievementsUserRepository = achievementsUserRepository;
        this.achievementsUserMapper = achievementsUserMapper;
    }

    public AchievementsUserDTO execute(Long userId, Long achievementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        AchievementsUser achivement = achievementsUserRepository
                .findByUserProgressIdAndAchievementProgressAchievementId(userId, achievementId);

        if (achivement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Achievement not found");
        }
        return achievementsUserMapper.toDTO(achivement);

    }
}
