package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CertificateRepository extends JpaRepository<Certificate, String>, CustomCertificateRepository {

    Certificate findCertificateByCertificateId(Long certificateId);

    List<Certificate> findCertificateByAddressAddressId(Long addressId);

    List<Certificate> findAll();

    List<Certificate> findCertificateByParameter(String parameter, Object val);

    List<Certificate> findCertificateBySetOfValues(String parameter, List<Object> values);

    List<Certificate> findCertificateByMinMaxRange(String parameter, Object min, Object max);

    Object convertToCorrectType(String parameter, String value);
}
