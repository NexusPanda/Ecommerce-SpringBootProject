package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.Exception.ResourceNotFoundException;
import com.ecommerce.Ecommerce.Model.Category;
import com.ecommerce.Ecommerce.Model.Product;
import com.ecommerce.Ecommerce.Payload.ProductDTO;
import com.ecommerce.Ecommerce.Payload.ProductResponse;
import com.ecommerce.Ecommerce.Repository.CategoryJPARepository;
import com.ecommerce.Ecommerce.Repository.ProductJPARepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceProductImplementation implements ProductService {
    @Autowired
    private CategoryJPARepository categoryJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO saveProduct(Long categoryId, Product product){
        Category category = categoryJPARepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        product.setCategory(category);
        product.setProductImage("default.png");
        double specialPrice = (product.getProductPrice() - (product.getDiscount() * 0.01) * product.getProductPrice());
        product.setSpecialPrice(specialPrice);
        Product saveProduct = productJPARepository.save(product);
        return modelMapper.map(saveProduct, ProductDTO.class);
    }

    public ProductResponse displayProduct(){
        List<Product> productList = productJPARepository.findAll();
        List<ProductDTO> productDTOS = productList.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDTOS(productDTOS);
        return productResponse;
    }

}
