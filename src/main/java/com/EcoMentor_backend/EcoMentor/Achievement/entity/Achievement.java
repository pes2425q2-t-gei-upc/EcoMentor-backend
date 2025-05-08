package com.EcoMentor_backend.EcoMentor.Achievement.entity;


import com.EcoMentor_backend.EcoMentor.User.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

    @NotNull
    private float achievementProgress;

    @ManyToMany
    @JoinTable(
            name = "tbl_achievements_user",
            joinColumns = @JoinColumn(name = "achievementId", referencedColumnName = "achievementId"),
            inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "id")
    )
    private List<User> users;


}