package com.EcoMentor_backend.EcoMentor.Achievement.entity;


import com.EcoMentor_backend.EcoMentor.Achievements_User.entity.Achievements_User;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String achievementName;

    @OneToMany(mappedBy = "achievement")
    private List<Achievements_User> progresses;

}