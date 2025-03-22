package com.EcoMentor_backend.EcoMentor.User.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddCertificateToUserUseCase {
    private final UserRepository userRepository;
    private final CertificateRepository certificateRepository;

    public AddCertificateToUserUseCase(UserRepository userRepository, CertificateRepository certificateRepository) {
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
    }

    public void execute(Long userId, Long certificateId) {
        Certificate certificate = certificateRepository.findCertificateByCertificateId(certificateId);
        if (certificate == null) {
            throw new IllegalArgumentException("Certificate not found");
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        certificate.getUsers().add(user);
        user.getCertificates().add(certificate);

        certificateRepository.save(certificate);
        userRepository.save(user);
    }
}