package com.EcoMentor_backend.EcoMentor.Address.entity;


import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "adress", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"addressName", "addressNumber"})
})
@Data
@AllArgsConstructor //creates automatically a constructor with all arguments
@NoArgsConstructor //creates automatically a constructor without any arguments
@Builder
public class Address {
    //TODO refactor to UUID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotNull
    private String addressName;
    @NotNull
    private String addressNumber;
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

    @OneToMany(mappedBy = "address")
    private List<Certificate> certificates;
    //TODO georefence?
}
