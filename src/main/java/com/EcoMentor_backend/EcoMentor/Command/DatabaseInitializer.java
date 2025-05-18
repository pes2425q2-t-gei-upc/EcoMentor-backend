package com.EcoMentor_backend.EcoMentor.Command;

import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.Achievement.infrastructure.repositories.AchievementRepository;
import com.EcoMentor_backend.EcoMentor.Role.entity.Role;
import com.EcoMentor_backend.EcoMentor.Role.entity.RoleName;
import com.EcoMentor_backend.EcoMentor.Role.infrastructure.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final AchievementRepository achievementRepository;

    public DatabaseInitializer(RoleRepository roleRepository,
                               AchievementRepository achievementRepository) {
        this.roleRepository = roleRepository;
        this.achievementRepository = achievementRepository;
    }


    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        for (RoleName roleName : RoleName.values()) {
            //We check if all of the existent role names are in the DB
            roleRepository.findByName(roleName).orElseGet(
                    //If not we insert them
                    () -> {
                        Role role = new Role();
                        role.setName(roleName);
                        return roleRepository.save(role);
                    }
            );
        }
        for (int i = 1; i <= 12; i++) {
            String achievementName = String.valueOf(i);
            achievementRepository.findByAchievementName(achievementName).orElseGet(
                    () -> {
                        Achievement achievement = new Achievement();
                        achievement.setAchievementName(achievementName);
                        return achievementRepository.save(achievement);
                    }
            );
        }


    }
}
