package com.EcoMentor_backend.EcoMentor.User.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.OfficialCertificateRepository;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AddOfficialCertificateToUserByDocumentIdUseCase {
    private final UserRepository userRepository;
    private final OfficialCertificateRepository officialCertificateRepository;

    public AddOfficialCertificateToUserByDocumentIdUseCase(UserRepository userRepository, OfficialCertificateRepository
                                                                   officialCertificateRepository) {
        this.userRepository = userRepository;
        this.officialCertificateRepository = officialCertificateRepository;
    }

    public void execute(Long userId, String documentId) {
        OfficialCertificate officialCertificate = officialCertificateRepository
                .findOfficialCertificateByDocumentId(documentId);
        if (officialCertificate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Oficial certificate not found");
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        officialCertificate.getUsers().add(user);
        user.getCertificates().add(officialCertificate);

        officialCertificateRepository.save(officialCertificate);
        userRepository.save(user);
    }
}