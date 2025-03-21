package com.EcoMentor_backend.EcoMentor.Certificate.entity;

import lombok.Getter;

@Getter
public enum Qualification {

    A(0),
    B(1),
    C(2),
    D(3),
    E(4),
    F(5),
    G(6);

    private final int value;

    Qualification(int value) {this.value = value;}
}
