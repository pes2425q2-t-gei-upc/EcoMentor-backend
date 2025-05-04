package com.EcoMentor_backend.EcoMentor.Address.useCases;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.GraphicDTO;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
@AllArgsConstructor
public class GetGraphValuesEnergyUseCase {
    private AddressRepository addressRepository;

    public List<GraphicDTO> execute(String town, String region, String province) {
        List<Address> addressList;

        if (town != null) {
            addressList = addressRepository.findByTown(town);
        } else if (region != null) {
            addressList = addressRepository.findByProvince(region);
        } else if (province != null) {
            addressList = addressRepository.findByProvince(province);
        } else {
            addressList = addressRepository.findAll();
        }
        int n = addressList.size();

        if (n == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No certificates found");
        }

        float insulation = 0;
        float windows = 0;

        for (Address address : addressList) {
            for (Certificate certificate : address.getCertificates()) {
                if (certificate.getCertificateType() == CertificateType.OFFICIAL) {
                    OfficialCertificate officialCertificate = (OfficialCertificate) certificate;
                    insulation += officialCertificate.getInsulation();
                    windows += officialCertificate.getWindowEfficiency();
                }
            }
        }
        GraphicDTO insulationDTO = new GraphicDTO("insulation", insulation / n);
        GraphicDTO windowsDTO = new GraphicDTO("windows", windows / n);
        return List.of(insulationDTO, windowsDTO);

    }
}
