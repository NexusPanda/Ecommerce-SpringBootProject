package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.Model.Product;
import com.ecommerce.Ecommerce.Payload.ProductDTO;
import com.ecommerce.Ecommerce.Payload.ProductResponse;

public interface ProductService {

    ProductDTO saveProduct(Long categoryId, Product product);
    ProductResponse displayProduct();
    ProductResponse displayProductByCategoryID(Long categoryId);

    ProductResponse displayProductByKeyword(String keyword);

    ProductDTO updateProduct(Product product, Long productId);

    ProductDTO deleteProduct(Long productId);
}
