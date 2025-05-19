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

    @GetMapping("/public/categories/{categoryId}/product")
    public ResponseEntity<ProductResponse> displayProductByCategoryID(@PathVariable Long categoryId){
        ProductResponse productResponse = productService.displayProductByCategoryID(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("public/product/keywords/{keyword}")
    public ResponseEntity<ProductResponse> displayProductByKeyword(@PathVariable String keyword){
        ProductResponse productResponse = productService.displayProductByKeyword('%' + keyword + '%');
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody Product product,
                                                    @PathVariable Long productId){
        ProductDTO productDTO = productService.updateProduct(product, productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @DeleteMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO productDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}
