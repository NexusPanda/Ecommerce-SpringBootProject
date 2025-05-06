package com.ecommerce.Ecommerce.Repository;

import com.ecommerce.Ecommerce.Model.Category;
import com.ecommerce.Ecommerce.Payload.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPARepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}
