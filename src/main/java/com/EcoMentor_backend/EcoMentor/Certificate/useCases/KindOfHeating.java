package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import lombok.Getter;

@Getter
public enum KindOfHeating {
    ELECTRICA(0),
    GEOTERMIA(1),
    DISTRICTE(2),
    BIOMASSA(3),
    GAS(4);

    private final int value;

    KindOfHeating(int value) {
        this.value = value;
    }

}
