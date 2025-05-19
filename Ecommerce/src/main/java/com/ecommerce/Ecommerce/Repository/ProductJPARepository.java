package com.ecommerce.Ecommerce.Repository;

import com.ecommerce.Ecommerce.Model.Category;
import com.ecommerce.Ecommerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJPARepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryOrderByProductPriceAsc(Category category);

    List<Product> findByProductNameLikeIgnoreCase(String keyword);
}
