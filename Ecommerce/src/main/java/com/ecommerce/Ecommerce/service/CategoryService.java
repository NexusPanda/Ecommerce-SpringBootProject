package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.Model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> displayCategory();
    void addCategory(Category category);
    String deleteCategory(Long categoryId);
    String updateCategory(Category category, Long categoryId);
}
