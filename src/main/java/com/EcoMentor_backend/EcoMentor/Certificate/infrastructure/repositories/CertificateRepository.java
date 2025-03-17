package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate,String> {
    Certificate findCertificateByCertificateId(Long certificateId);
    List<Certificate> findCertificateByAddress_AddressId(Long addressId);
    List<Certificate> findAll();

    boolean existsByCertificateId(Long certificateId);
}
