package com.EcoMentor_backend.EcoMentor.Certificate.entity;


import lombok.Getter;

@Getter
public enum CertificateType {

    OFFICIAL(0),
    UNOFFICIAL(1);

    private final int value;

    CertificateType(int value) {
        this.value = value;
    }
}
