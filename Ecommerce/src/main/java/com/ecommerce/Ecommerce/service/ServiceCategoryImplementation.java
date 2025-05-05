package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.Exception.APIException;
import com.ecommerce.Ecommerce.Exception.ResourceNotFoundException;
import com.ecommerce.Ecommerce.Model.Category;
import com.ecommerce.Ecommerce.Repository.JPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCategoryImplementation implements CategoryService{
    @Autowired
    private JPARepository jpaRepostry;

    @Override
    public List<Category> displayCategory() {
        if(jpaRepostry.findAll().isEmpty())
            throw new APIException("No Categories Found !!!");
        return jpaRepostry.findAll();
    }

    @Override
    public void addCategory(Category category) {
        Category savedCategory = jpaRepostry.findByCategoryName(category.getCategoryName());
        if(savedCategory != null)
            throw new APIException("Category Name " + category.getCategoryName() + " already exist !!!");
        jpaRepostry.save(category);
    }

    @Override
    public String deleteCategory(Long categoryID){
        Category deleteCategory = jpaRepostry.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryID));
        jpaRepostry.delete(deleteCategory);
        return "Category Id " + categoryID + " successfully removed !!!";
    }

    @Override
    public String updateCategory(Category category, Long categoryID){
        Category updateCategory = jpaRepostry.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryID));
        category.setCategoryID(categoryID);
        jpaRepostry.save(category);
        return "Category Id " + categoryID + " is Updated !!!";
    }
}