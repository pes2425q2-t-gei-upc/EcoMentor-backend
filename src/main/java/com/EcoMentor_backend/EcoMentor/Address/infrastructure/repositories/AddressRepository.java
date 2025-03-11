package com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsAddressByAddressNameAndAddressNumber(String addressName, String addressNumber);
    Address findByAddressId(Long addressId);
    List<Address> findAll();


}
