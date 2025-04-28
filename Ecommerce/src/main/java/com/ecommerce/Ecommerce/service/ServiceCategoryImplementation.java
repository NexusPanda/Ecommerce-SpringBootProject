package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.Model.Category;
import com.ecommerce.Ecommerce.Repository.JPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCategoryImplementation implements CategoryService{
    @Autowired
    private JPARepository jpaRepostry;

    @Override
    public List<Category> displayCategory() {
        return jpaRepostry.findAll();
    }

    @Override
    public void addCategory(Category category) {
        jpaRepostry.save(category);
    }

    @Override
    public String deleteCategory(Long categoryID){
        Category deleteCategory = jpaRepostry.findById(categoryID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found !!!"));
        jpaRepostry.delete(deleteCategory);
        return "Category Id " + categoryID + " successfully removed !!!";
    }

    @Override
    public String updateCategory(Category category, Long categoryID){
        Category updateCategory = jpaRepostry.findById(categoryID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found !!!"));
        category.setCategoryID(categoryID);
        jpaRepostry.save(category);
        return "Category Id " + categoryID + " is Updated !!!";
    }
}