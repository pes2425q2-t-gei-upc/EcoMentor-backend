package com.EcoMentor_backend.EcoMentor.Certificate.entity;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

    Qualification(int value) {
        this.value = value;
    }

    @JsonValue
    public String toJson() {
        return this.name();
    }

    @JsonCreator
    public static Qualification fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null; // Permite valores vacíos o null sin error
        }
        try {
            return Qualification.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Valor inválido para Qualification: " + value);
        }
    }
}

