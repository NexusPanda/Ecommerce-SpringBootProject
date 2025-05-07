package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.Payload.CategoryDTO;
import com.ecommerce.Ecommerce.Payload.CategoryResponse;

public interface CategoryService {

    CategoryResponse displayCategory(int pageNumber, int pageSize);
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
