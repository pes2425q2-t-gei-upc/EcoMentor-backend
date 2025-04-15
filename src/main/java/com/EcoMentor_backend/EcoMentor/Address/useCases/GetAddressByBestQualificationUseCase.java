package com.EcoMentor_backend.EcoMentor.Address.useCases;


import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories.AddressRepository;
import com.EcoMentor_backend.EcoMentor.Address.useCases.dto.AddressDTOBestQualification;
import com.EcoMentor_backend.EcoMentor.Address.useCases.mapper.AddressMapper;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Certificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.CertificateType;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.OfficialCertificate;
import com.EcoMentor_backend.EcoMentor.Certificate.entity.Qualification;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class GetAddressByBestQualificationUseCase {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public GetAddressByBestQualificationUseCase(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public List<AddressDTOBestQualification> execute(String town, Integer zipcode, Double latitude, Double longitude,
                                                                                                    Integer radius) {

        List<Address> addresses = new ArrayList<>();
        if (town != null) {
            List<Address> townAddresses = addressRepository.findByTown(town);
            if (townAddresses.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No addresses found for the specified town");
            }
            addresses.addAll(townAddresses);
        } else if (zipcode != null) {
            List<Address> zipcodeAddresses = addressRepository.findByZipcode(zipcode);
            if (zipcodeAddresses.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No addresses found for the specified zipcode");
            }
            addresses.addAll(zipcodeAddresses);
        } else if (latitude != null && longitude != null && radius != null) {
            radius = radius * 1000;
            Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
            List<Address> nearbyAddresses = addressRepository.findAddressesWithinDistance(location, radius);
            if (nearbyAddresses.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "No addresses found within the specified radius");
            }
            addresses.addAll(nearbyAddresses);
        }

        if (addresses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No addresses found");
        }

        HashMap<Qualification, List<AddressDTOBestQualification>> qualificationMap = new HashMap<>();

        for (Qualification qualification : Qualification.values()) {
            qualificationMap.put(qualification, new ArrayList<>());
        }

        for (Address address : addresses) {
            for (Certificate certificate : address.getCertificates()) {
                if (certificate.getCertificateType() == CertificateType.OFFICIAL) {
                    Qualification qualificationAddress = getQualification((OfficialCertificate) certificate);
                    AddressDTOBestQualification addressDTO = addressMapper.toDTOWithBestQualification(address);
                    addressDTO.setQualification(qualificationAddress.toJson());
                    qualificationMap.get(qualificationAddress).add(addressDTO);
                }
            }
        }

        if (!qualificationMap.get(Qualification.A).isEmpty()) {
            return qualificationMap.get(Qualification.A);
        } else if (!qualificationMap.get(Qualification.B).isEmpty()) {
            return qualificationMap.get(Qualification.B);
        } else if (!qualificationMap.get(Qualification.C).isEmpty()) {
            return qualificationMap.get(Qualification.C);
        } else if (!qualificationMap.get(Qualification.D).isEmpty()) {
            return qualificationMap.get(Qualification.D);
        } else if (!qualificationMap.get(Qualification.E).isEmpty()) {
            return qualificationMap.get(Qualification.E);
        } else if (!qualificationMap.get(Qualification.F).isEmpty()) {
            return qualificationMap.get(Qualification.F);
        } else {
            return qualificationMap.get(Qualification.G);
        }


    }

    private Qualification getQualification(OfficialCertificate certificate) {
        int nonRenewable = certificate.getNonRenewablePrimaryQualification() != null
                ? certificate.getNonRenewablePrimaryQualification().getValue()
                : 6; // Worst qualification

        int co2 = certificate.getCo2Qualification() != null
                ? certificate.getCo2Qualification().getValue()
                : 6;

        int heating = certificate.getHeatingQualification() != null
                ? certificate.getHeatingQualification().getValue()
                : 6;

        int refrigeration = certificate.getRefrigerationQualification() != null
                ? certificate.getRefrigerationQualification().getValue()
                : 6;

        int acs = certificate.getAcsQualification() != null
                ? certificate.getAcsQualification().getValue()
                : 6;

        int lighting = certificate.getLightingQualification() != null
                ? certificate.getLightingQualification().getValue()
                : 6;


        int sum = nonRenewable + co2 + heating + refrigeration + acs + lighting;
        int average = sum / 6;
        return Qualification.fromValue(average);
    }

}
