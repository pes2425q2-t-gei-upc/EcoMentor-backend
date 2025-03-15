package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate,String> {
    Certificate findCertificateBycertificateId(Long certificateId);
    List<Certificate> findCertificateByaddress(Long addressId);
    List<Certificate> findAll();

}
