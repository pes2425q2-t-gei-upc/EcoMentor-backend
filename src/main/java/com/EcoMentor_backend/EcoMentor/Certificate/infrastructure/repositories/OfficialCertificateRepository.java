package com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OfficialCertificateRepository extends JpaRepository<OfficialCertificate, Long> {

    OfficialCertificate findOfficialCertificateByDocumentId(@NotNull String documentId);

}
