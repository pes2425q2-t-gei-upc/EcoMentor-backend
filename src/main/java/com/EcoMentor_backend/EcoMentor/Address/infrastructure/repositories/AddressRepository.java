package com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsAddressByAddressNameAndAddressNumber(String addressName, String addressNumber);
    Address findByAddressId(Long addressId);
    List<Address> findAll();
    @Query(value = """
    SELECT * FROM address 
    WHERE ST_DWithin(location, CAST(:point AS geography), :radius)
    """, nativeQuery = true)
    List<Address> findAddressesWithinDistance(@Param("point") Point point, @Param("radius") double radius);



}
