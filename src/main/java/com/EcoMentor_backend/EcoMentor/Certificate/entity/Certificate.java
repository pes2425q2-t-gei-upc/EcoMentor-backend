package com.EcoMentor_backend.EcoMentor.Certificate.entity;


import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "certificate")
@Data
@AllArgsConstructor //creates automatically a constructor with all arguments
@NoArgsConstructor //creates automatically a constructor without any arguments
@Builder
public class Certificate {
    @Id
    private String certificateId; //TODO change to UUIDV7

    @NotNull
    private CertificateType certificateType;

    @ManyToOne
    @JoinColumn( name = "addressId", referencedColumnName = "addressId")
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "tbl_certificate_recommendations",
            joinColumns = @JoinColumn (
                    name = " recommendationId",
                    referencedColumnName = "recommendationId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "recommendationId",
                    referencedColumnName = "recommendationId"
            )
    )
    private ArrayList<Recommendation> recommendations;
    //TODO decide definitive data structure for recommendations

}
