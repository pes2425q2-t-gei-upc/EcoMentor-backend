package com.EcoMentor_backend.EcoMentor.Certificate.useCases;

import lombok.Getter;

@Getter
public enum KindOfRefrigeration {
    ELECTRICA(0),
    GEOTERMIA(1),
    DISTRICTE(2);

    private final int value;

    KindOfRefrigeration(int value) {
        this.value = value;
    }
}
