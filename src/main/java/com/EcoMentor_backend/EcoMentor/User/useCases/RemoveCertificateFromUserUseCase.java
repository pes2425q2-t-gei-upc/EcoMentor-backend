package com.EcoMentor_backend.EcoMentor.User.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class RemoveCertificateFromUserUseCase {
    private final UserRepository userRepository;
    private final CertificateRepository certificateRepository;

    public void execute(Long userId, Long certificateId) {
        Certificate certificate = certificateRepository.findCertificateByCertificateId(certificateId);

        if (certificate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found");
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (!user.getCertificates().contains(certificate)) {
            throw new IllegalArgumentException("User does not have this certificate");
        }

        user.getCertificates().remove(certificate);
        certificate.getUsers().remove(user);

        userRepository.save(user);
        certificateRepository.save(certificate);
    }
}
