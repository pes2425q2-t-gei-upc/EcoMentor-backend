package com.EcoMentor_backend.EcoMentor.Achievements_User.entity;


import com.EcoMentor_backend.EcoMentor.Achievement.entity.Achievement;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "achievement_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AchievementsUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long progressId;

    @NotNull
    private String progressStatus;

    @ManyToOne
    @JoinColumn(name = "achievementId", nullable = false, referencedColumnName = "achievementId")
    private Achievement achievementProgress;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false, referencedColumnName = "userId")
    private User userProgress;
}
