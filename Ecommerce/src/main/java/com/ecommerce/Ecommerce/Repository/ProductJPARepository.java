package com.ecommerce.Ecommerce.Repository;

import com.ecommerce.Ecommerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJPARepository extends JpaRepository<Product, Long> {
}
