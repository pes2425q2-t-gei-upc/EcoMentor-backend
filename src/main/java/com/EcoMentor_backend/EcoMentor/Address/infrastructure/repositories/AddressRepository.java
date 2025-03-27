package com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import java.util.List;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, CustomAddressRepository {

    boolean existsAddressByAddressNameAndAddressNumber(String addressName, String addressNumber);

    Address findByAddressId(Long addressId);

    List<Address> findAll();

    List<Address> findByProvince(String province);

    List<Address> findByTown(String town);

    @Query(value = """
            SELECT * FROM address 
            WHERE ST_DWithin(location, CAST(:point AS geography), :radius)
            """, nativeQuery = true)
    List<Address> findAddressesWithinDistance(@Param("point") Point point, @Param("radius") double radius);

    @Query(value = """
        SELECT * FROM address
        WHERE ST_Within(location, ST_MakeEnvelope(:minLongitude, :minLatitude, :maxLongitude, :maxLatitude, 4326))
        """, nativeQuery = true)

    List<Address> findAddressesWithinBoundingBox(
            @Param("minLatitude") double minLatitude,
            @Param("maxLatitude") double maxLatitude,
            @Param("minLongitude") double minLongitude,
            @Param("maxLongitude") double maxLongitude
    );

    List<Address> findAddresByCertificateByParameter(String parameter, Object val, double minLatitude,
                                                 double maxLatitude, double minLongitude,
                                                 double maxLongitude);


    Address findAddressByAddressNameAndAddressNumber(String addressName, String addressNumber);
}
