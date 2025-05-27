package com.EcoMentor_backend.EcoMentor.Achievement.entity;


import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.AchievementsUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "achievement")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Achievement {
    //TODO change to UUID?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long achievementId;

    @Column(unique = true)
    @NotNull
    private String achievementName;

    private int type;

    private int maxProgress;

    @OneToMany(mappedBy = "achievementProgress")
    private List<AchievementsUser> progresses = new ArrayList<>();

}