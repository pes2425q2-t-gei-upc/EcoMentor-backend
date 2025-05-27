package com.EcoMentor_backend.EcoMentor.User.useCases;

import com.EcoMentor_backend.EcoMentor.Achievements_User.useCases.AchivementProgressUseCase;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@AllArgsConstructor
public class AddCertificateToUserUseCase {
    private final UserRepository userRepository;
    private final CertificateRepository certificateRepository;
    private final AchivementProgressUseCase achivementProgressUseCase;


    public void execute(Long userId, Long certificateId) {
        Certificate certificate = certificateRepository.findCertificateByCertificateId(certificateId);
        if (certificate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found");
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (user.getCertificates().contains(certificate)) {
            throw new IllegalArgumentException("User already has this certificate");
        }


        certificate.getUsers().add(user);
        user.getCertificates().add(certificate);

        certificateRepository.save(certificate);
        userRepository.save(user);
        long achivement = 1;
        achivementProgressUseCase.execute(userId, achivement);
    }
}