package com.EcoMentor_backend.EcoMentor.Achievements_User.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementsUserRepository extends JpaRepository<AchievementsUser, Long> {
}
