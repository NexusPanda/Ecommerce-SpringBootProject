package com.ecommerce.Ecommerce.Repository;

import com.ecommerce.Ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJPARepository extends JpaRepository<Long, User> {
    Optional<User> findByUsername(String username);
}
