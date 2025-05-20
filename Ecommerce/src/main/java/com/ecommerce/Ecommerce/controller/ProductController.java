package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.Payload.ProductDTO;
import com.ecommerce.Ecommerce.Payload.ProductResponse;
import com.ecommerce.Ecommerce.config.DefaultConfig;
import com.ecommerce.Ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> saveProduct(@PathVariable Long categoryId,
                                                  @Valid @RequestBody ProductDTO productDTO){
        ProductDTO savedproductDTO = productService.saveProduct(categoryId, productDTO);
        return new ResponseEntity<>(savedproductDTO, HttpStatus.CREATED);
    }

    @GetMapping("/admin/product")
    public ResponseEntity<ProductResponse> displayProduct(@RequestParam(name = "pageNumber",
                                                          defaultValue = DefaultConfig.PAGE_NUMBER, required = false) int pageNumber,
                                                          @RequestParam(name = "pageSize",
                                                          defaultValue = DefaultConfig.PAGE_SIZE, required = false) int pageSize,
                                                          @RequestParam(name = "sortBy",
                                                          defaultValue = DefaultConfig.SORT_BY_PRODUCT, required = false) String sortBy,
                                                          @RequestParam(name = "sortDir",
                                                          defaultValue = DefaultConfig.SORT_DIR, required = false) String sortDir){
        ProductResponse productResponse = productService.displayProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/product")
    public ResponseEntity<ProductResponse> displayProductByCategoryID(@PathVariable Long categoryId,
                                                                      @RequestParam(name = "pageNumber",
                                                                              defaultValue = DefaultConfig.PAGE_NUMBER, required = false) int pageNumber,
                                                                      @RequestParam(name = "pageSize",
                                                                              defaultValue = DefaultConfig.PAGE_SIZE, required = false) int pageSize,
                                                                      @RequestParam(name = "sortBy",
                                                                              defaultValue = DefaultConfig.SORT_BY_PRODUCT, required = false) String sortBy,
                                                                      @RequestParam(name = "sortDir",
                                                                              defaultValue = DefaultConfig.SORT_DIR, required = false) String sortDir){
        ProductResponse productResponse = productService.displayProductByCategoryID(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/product/keywords/{keyword}")
    public ResponseEntity<ProductResponse> displayProductByKeyword(@PathVariable String keyword,
                                                                   @RequestParam(name = "pageNumber",
                                                                           defaultValue = DefaultConfig.PAGE_NUMBER, required = false) int pageNumber,
                                                                   @RequestParam(name = "pageSize",
                                                                           defaultValue = DefaultConfig.PAGE_SIZE, required = false) int pageSize,
                                                                   @RequestParam(name = "sortBy",
                                                                           defaultValue = DefaultConfig.SORT_BY_PRODUCT, required = false) String sortBy,
                                                                   @RequestParam(name = "sortDir",
                                                                           defaultValue = DefaultConfig.SORT_DIR, required = false) String sortDir){
        ProductResponse productResponse = productService.displayProductByKeyword('%' + keyword + '%', pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,
                                                    @PathVariable Long productId){
        ProductDTO savedproductDTO = productService.updateProduct(productDTO, productId);
        return new ResponseEntity<>(savedproductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO productDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updatedProduct = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}
