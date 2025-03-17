package com.EcoMentor_backend.EcoMentor.Address.entity;


import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "address", uniqueConstraints = {
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
    @Column(columnDefinition = "geometry(Point, 4326)") // EPSG 4326 para coordenadas geográficas
    private Point location; // Es la longitud y latitud de la dirección

    @OneToMany(mappedBy = "address")
    private List<Certificate> certificates;
    //TODO georefence?
}
