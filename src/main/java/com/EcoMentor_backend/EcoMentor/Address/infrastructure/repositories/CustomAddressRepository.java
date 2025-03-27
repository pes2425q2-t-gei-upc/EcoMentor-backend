package com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import java.util.List;

public interface CustomAddressRepository {

    List<Address> findAddresByCertificateByParameter(String parameter, Object val, double minLatitude,
                                                     double maxLatitude, double minLongitude,
                                                     double maxLongitude);

}
