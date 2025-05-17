package com.ecommerce.Ecommerce.Repository;

import com.ecommerce.Ecommerce.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJPARepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}
