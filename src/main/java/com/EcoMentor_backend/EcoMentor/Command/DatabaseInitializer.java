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
            int ii = i;
            achievementRepository.findByAchievementName(achievementName).orElseGet(
                    () -> {
                        Achievement achievement = new Achievement();
                        achievement.setAchievementName(achievementName);
                        if (ii  == 7 || ii == 3) {
                            achievement.setMaxProgress(3);
                        } else if (ii == 10) {
                            achievement.setMaxProgress(5);
                        } else {
                            achievement.setMaxProgress(1);
                        }

                        if (ii == 1 || ii == 11 || ii == 12) {
                            achievement.setType(1);
                        } else if (ii == 2 || ii == 4 || ii == 3) {
                            achievement.setType(2);
                        } else {
                            achievement.setType(3);
                        }

                        return achievementRepository.save(achievement);
                    }
            );
        }


    }
}
