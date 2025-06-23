//package com.ecommerce.Ecommerce.Repository;
//
//import com.ecommerce.Ecommerce.Model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface UserJPARepository extends JpaRepository<User, Long> {
//    Optional<User> findByUserName(String username);
//
//    User findByUsername(String username);
//}