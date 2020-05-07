package io.spring.quiz_online.repositories;

import io.spring.quiz_online.model.Role;
import io.spring.quiz_online.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(RoleEnum roleName);
}
