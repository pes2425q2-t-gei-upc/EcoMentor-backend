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
public class GetGraphValuesPerformanceUseCase {
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

        float nonRenewablePrimaryEnergy = 0;
        float co2Emissions = 0;
        float finalEnergyConsumption = 0;
        float annualCost = 0;

        for (Address address : addressList) {
            for (Certificate certificate : address.getCertificates()) {
                if (certificate.getCertificateType() == CertificateType.OFFICIAL) {
                    OfficialCertificate officialCertificate = (OfficialCertificate) certificate;
                    nonRenewablePrimaryEnergy += officialCertificate.getNonRenewablePrimaryEnergy();
                    co2Emissions += officialCertificate.getCo2Emissions();
                    finalEnergyConsumption += officialCertificate.getFinalEnergyConsumption();
                    annualCost += officialCertificate.getAnnualCost();
                }
            }
        }

        GraphicDTO nonPrimar = new GraphicDTO("nonRenewablePrimaryEnergy", nonRenewablePrimaryEnergy / n);
        GraphicDTO co2EmissionsDTO = new GraphicDTO("co2Emissions", co2Emissions / n);
        GraphicDTO finalEnergyDTO = new GraphicDTO("finalEnergyConsumption", finalEnergyConsumption / n);
        GraphicDTO annualCostDTO = new GraphicDTO("annualCost", annualCost / n);

        return List.of(nonPrimar, co2EmissionsDTO, finalEnergyDTO, annualCostDTO);

    }
}
