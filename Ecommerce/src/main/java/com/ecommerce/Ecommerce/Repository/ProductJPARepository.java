package com.ecommerce.Ecommerce.Repository;

import com.ecommerce.Ecommerce.Model.Category;
import com.ecommerce.Ecommerce.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJPARepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryOrderByProductPriceAsc(Pageable pageable, Category category);

    Page<Product> findByProductNameLikeIgnoreCase(Pageable pageable, String keyword);
}
