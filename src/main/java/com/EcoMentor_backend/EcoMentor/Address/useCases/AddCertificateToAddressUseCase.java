package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.infrastructure.repositories.CertificateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddCertificateToAddressUseCase {
    private final AddressRepository addressRepository;
    private final CertificateRepository certificateRepository;

    public AddCertificateToAddressUseCase(AddressRepository addressRepository,
                                          CertificateRepository certificateRepository) {
        this.addressRepository = addressRepository;
        this.certificateRepository = certificateRepository;
    }

    public void execute(Long addressId, Long certificateId) {
        Certificate certificate = certificateRepository.findCertificateByCertificateId(certificateId);
        if (certificate == null) {
            throw new IllegalArgumentException("Certificate not found");
        }
        Address address = addressRepository.findByAddressId(addressId);
        if (address == null) {
            throw new IllegalArgumentException("Address not found");
        }
        certificate.setAddress(address);

        address.getCertificates().add(certificate);

        certificateRepository.save(certificate);
        addressRepository.save(address);
    }
}
