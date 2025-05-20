package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.Payload.ProductDTO;
import com.ecommerce.Ecommerce.Payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    ProductDTO saveProduct(Long categoryId, ProductDTO product);
    ProductResponse displayProduct();
    ProductResponse displayProductByCategoryID(Long categoryId);

    ProductResponse displayProductByKeyword(String keyword);

    ProductDTO updateProduct(ProductDTO product, Long productId);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
