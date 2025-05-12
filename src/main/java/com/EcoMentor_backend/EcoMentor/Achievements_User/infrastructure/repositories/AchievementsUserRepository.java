package com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AchievementsUserRepository extends JpaRepository<AchievementsUser, Long> {
    List<AchievementsUser> findByUserProgress(User user);
}
