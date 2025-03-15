package com.EcoMentor_backend.EcoMentor.Certificate.entity;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "certificate")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId; //TODO change to UUIDV7

    @NotNull
    private CertificateType certificateType;

    @ManyToOne
    @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "tbl_certificate_recommendations",
            joinColumns = @JoinColumn(name = "certificateId", referencedColumnName = "certificateId"),
            inverseJoinColumns = @JoinColumn(name = "recommendationId", referencedColumnName = "recommendationId")
    )
    private ArrayList<Recommendation> recommendations;
    //TODO decide definitive data structure for recommendations
}