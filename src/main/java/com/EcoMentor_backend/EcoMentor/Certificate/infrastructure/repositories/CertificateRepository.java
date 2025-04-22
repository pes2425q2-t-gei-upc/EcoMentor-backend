package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface CertificateRepository extends JpaRepository<Certificate, String>, CustomCertificateRepository {

    Certificate findCertificateByCertificateId(Long certificateId);

    List<Certificate> findCertificateByAddressAddressId(Long addressId);

    List<Certificate> findAll();

    List<Certificate> findCertificateByParameter(String parameter, Object val, double minLatitude,
                                                 double maxLatitude, double minLongitude,
                                                 double maxLongitude);

    List<Certificate> findCertificateBySetOfValues(String parameter, List<Object> values);

    List<Certificate> findCertificateByMinMaxRange(String parameter, Object min, Object max);

    Object convertToCorrectType(String parameter, String value);


    @Query("SELECT c FROM Certificate c JOIN c.users u WHERE c.certificateId = :certificateId AND u.id = :userId")
    Optional<Certificate> findByIdAndUserId(@Param("certificateId") Long certificateId, @Param("userId") Long userId);
}
