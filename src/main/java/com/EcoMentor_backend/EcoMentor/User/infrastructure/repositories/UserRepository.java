package com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findUserById(Long id);
    boolean existsByEmail(String email);

    List<User> findAll();
}
