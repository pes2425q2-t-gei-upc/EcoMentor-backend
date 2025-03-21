package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomCertificateRepository {
    List<Certificate> findCertificateByParameter(String parameter, Object value);
    List<Certificate> findCertificateBySetOfValues(String parameter, List<Object> values);
    List<Certificate> findCertificateByMinMaxRange(String parameter, Object min, Object max);
    Object convertToCorrectType(String parameter, String value);
}
