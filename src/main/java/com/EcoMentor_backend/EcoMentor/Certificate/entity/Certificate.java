package com.EcoMentor_backend.EcoMentor.Certificate.entity;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Recommendation.entity.Recommendation;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;




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

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
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
    private List<Recommendation> recommendations;

    @ManyToMany(mappedBy = "certificates")
    private List<User> users = new ArrayList<>();
}