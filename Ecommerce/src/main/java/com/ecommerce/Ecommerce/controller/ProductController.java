package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.Model.Product;
import com.ecommerce.Ecommerce.Payload.ProductDTO;
import com.ecommerce.Ecommerce.Payload.ProductResponse;
import com.ecommerce.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> saveProduct(@PathVariable Long categoryId,
                                                  @RequestBody Product product){
        ProductDTO productDTO = productService.saveProduct(categoryId, product);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @GetMapping("/admin/product")
    public ResponseEntity<ProductResponse> displayProduct(){
        ProductResponse productResponse = productService.displayProduct();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
}
