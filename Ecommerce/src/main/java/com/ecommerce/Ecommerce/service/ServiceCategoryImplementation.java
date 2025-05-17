package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.Exception.APIException;
import com.ecommerce.Ecommerce.Exception.ResourceNotFoundException;
import com.ecommerce.Ecommerce.Model.Category;
import com.ecommerce.Ecommerce.Payload.CategoryDTO;
import com.ecommerce.Ecommerce.Payload.CategoryResponse;
import com.ecommerce.Ecommerce.Repository.CategoryJPARepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCategoryImplementation implements CategoryService{
    @Autowired
    private CategoryJPARepository jpaRepostry;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse displayCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> pageablePage = jpaRepostry.findAll(pageable);

        List<Category> categoryList = pageablePage.getContent();
        if(categoryList.isEmpty())
            throw new APIException("No Categories Found !!!");
        List<CategoryDTO>  categoryDTOList = categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        CategoryResponse categoryResponse1 = new CategoryResponse();
        categoryResponse1.setCategoryDTOList(categoryDTOList);
        categoryResponse1.setPageNumber(pageablePage.getNumber());
        categoryResponse1.setPageSize(pageablePage.getSize());
        categoryResponse1.setTotalPages(pageablePage.getTotalPages());
        categoryResponse1.setLastPage(pageablePage.isLast());
        return categoryResponse1;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category categoryByDB = jpaRepostry.findByCategoryName(categoryDTO.getCategoryName());
        if(categoryByDB != null)
            throw new APIException("Category Name " + categoryDTO.getCategoryName() + " already exist !!!");
        Category savedCategory = modelMapper.map(categoryDTO, Category.class);
        jpaRepostry.save(savedCategory);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryID){
        Category deleteCategory = jpaRepostry.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryID));
        jpaRepostry.delete(deleteCategory);
        return modelMapper.map(deleteCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryID){
        Category updateCategoryDB = jpaRepostry.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryID));
        Category savedCategory = modelMapper.map(categoryDTO, Category.class);
        savedCategory.setCategoryID(categoryID);
        updateCategoryDB = jpaRepostry.save(savedCategory);
        return modelMapper.map(updateCategoryDB, CategoryDTO.class);
    }
}