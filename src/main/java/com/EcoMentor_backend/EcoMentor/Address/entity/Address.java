package com.EcoMentor_backend.EcoMentor.Address.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "adress")
public class Address {
    //TODO refactor to UUID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotNull
    private String addressName;
    @NotNull
    private String addresNumber;
    @NotNull
    private Integer zipcode;
    @NotNull
    private String town;
    @NotNull
    private String region;
    @NotNull
    private String province;
    @NotNull
    private float longitude;
    @NotNull
    private float latitude;
    //TODO georefence?
}
