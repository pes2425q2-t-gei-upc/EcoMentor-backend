package com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories;

import com.EcoMentor_backend.EcoMentor.User.entity.Role;
import com.EcoMentor_backend.EcoMentor.User.entity.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
