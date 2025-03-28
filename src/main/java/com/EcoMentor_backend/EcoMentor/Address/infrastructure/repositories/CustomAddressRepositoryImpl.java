package com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;

public class CustomAddressRepositoryImpl implements CustomAddressRepository {
    private final EntityManager entityManager;

    public CustomAddressRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Address> findAddresByCertificateByParameter(String parameter, Object value, double minLatitude,
                                                            double maxLatitude, double minLongitude,
                                                            double maxLongitude) {

        String jpql = "SELECT a FROM  Address a"
                + " JOIN a.certificates c"
                + " WHERE c." + parameter + " = :value "
                + "AND ST_Within(a.location, ST_MakeEnvelope(:minLongitude, :minLatitude, :maxLongitude,"
                + " :maxLatitude, 4326))";

        Query query = entityManager.createQuery(jpql);

        query.setParameter("value", value);
        query.setParameter("minLatitude", minLatitude);
        query.setParameter("maxLatitude", maxLatitude);
        query.setParameter("minLongitude", minLongitude);
        query.setParameter("maxLongitude", maxLongitude);

        return query.getResultList();
    }


}
