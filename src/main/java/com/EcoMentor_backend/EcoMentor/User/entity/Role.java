package com.EcoMentor_backend.EcoMentor.User.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "roles")
public class Role {


    @Getter
    @Setter
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Getter
    @Setter
    @Column(length = 60, unique = true)
    private RoleName name;

    public Role() {
    }


}
