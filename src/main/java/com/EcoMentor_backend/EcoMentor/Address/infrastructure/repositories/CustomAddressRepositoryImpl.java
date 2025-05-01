package com.EcoMentor_backend.EcoMentor.Address.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.Address.entity.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Address> findAddressByCertificateByParameters(Map<String, Object> parameters,
                                                              double minLatitude,
                                                              double maxLatitude,
                                                              double minLongitude,
                                                              double maxLongitude) {
        StringBuilder jpql = new StringBuilder("SELECT a FROM Address a JOIN a.certificates c WHERE ");

        // Añadir condiciones dinámicamente
        List<String> conditions = new ArrayList<>();
        for (String key : parameters.keySet()) {
            conditions.add("c." + key + " = :" + key);
        }

        // Añadir condición geográfica
        jpql.append(String.join(" AND ", conditions));
        jpql.append(" AND ST_Within(a.location, ST_MakeEnvelope(:minLongitude, :minLatitude, :maxLongitude,"
                + " :maxLatitude, 4326))");

        Query query = entityManager.createQuery(jpql.toString());

        // Establecer parámetros dinámicos
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        // Establecer parámetros de geolocalización
        query.setParameter("minLatitude", minLatitude);
        query.setParameter("maxLatitude", maxLatitude);
        query.setParameter("minLongitude", minLongitude);
        query.setParameter("maxLongitude", maxLongitude);

        return query.getResultList();
    }


}
