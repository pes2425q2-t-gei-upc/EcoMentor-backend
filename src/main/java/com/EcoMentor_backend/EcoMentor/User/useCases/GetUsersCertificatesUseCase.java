package com.EcoMentor_backend.EcoMentor.User.useCases;

import com.EcoMentor_backend.EcoMentor.Certificate.useCases.dto.CertificateDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.useCases.mapper.CertificateMapper;
import com.EcoMentor_backend.EcoMentor.User.entity.User;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class GetUsersCertificatesUseCase {
    private final UserRepository userRepository;
    private final CertificateMapper certificateMapper;

    public GetUsersCertificatesUseCase(UserRepository userRepository, CertificateMapper certificateMapper) {
        this.userRepository = userRepository;
        this.certificateMapper = certificateMapper;
    }

    public List<CertificateDTO> execute(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getCertificates().stream()
                .map(certificateMapper::toDTO)
                .collect(Collectors.toList());
    }
}