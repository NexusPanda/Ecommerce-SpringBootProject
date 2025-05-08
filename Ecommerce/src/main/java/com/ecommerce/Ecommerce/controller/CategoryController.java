package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.Payload.CategoryDTO;
import com.ecommerce.Ecommerce.Payload.CategoryResponse;
import com.ecommerce.Ecommerce.config.DefaultConfig;
import com.ecommerce.Ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/admin/categories")
    //@RequestMapping(value = "/admin/categories" , method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> displayCategory(@RequestParam(name = "pageNumber",
                                                            defaultValue = DefaultConfig.PAGE_NUMBER, required = false) int pageNumber,
                                                            @RequestParam(name = "pageSize",
                                                            defaultValue = DefaultConfig.PAGE_SIZE, required = false) int pageSize,
                                                            @RequestParam(name = "sortBy",
                                                            defaultValue = DefaultConfig.SORT_BY, required = false) String sortBy,
                                                            @RequestParam(name = "sortDir",
                                                            defaultValue = DefaultConfig.SORT_DIR, required = false) String sortDir){
        CategoryResponse categoryResponse = categoryService.displayCategory(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    //@RequestMapping(value = "/public/categories", method = RequestMethod.POST)
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.addCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
        CategoryDTO savedCategoryDTO = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);

    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId){
        CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }
}
