package com.ecommerce.Ecommerce.Repository;

import com.ecommerce.Ecommerce.Model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPARepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(@NotBlank @Size(min = 5, message = "Minimum 5 Character must be present") String categoryName);
}
