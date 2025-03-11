package com.EcoMentor_backend.EcoMentor.User.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "appUser")
@Data
@AllArgsConstructor //creates automatically a constructor with all arguments
@NoArgsConstructor //creates automatically a constructor without any arguments
@Builder
public class User {
    //TODO refactor to UUID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotNull
    private String name;

    @Column(unique = true, length = 50)
    @NotNull
    private String email;

    @Column(length = 50)
    @NotNull
    private String password;

}
