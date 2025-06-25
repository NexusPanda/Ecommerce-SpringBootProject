package com.ecommerce.Ecommerce.Repository;

import com.ecommerce.Ecommerce.Model.AppRole;
import com.ecommerce.Ecommerce.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJPARepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
