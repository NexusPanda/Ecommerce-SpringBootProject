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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ServiceProductImplementation implements ProductService {
    @Autowired
    private CategoryJPARepository categoryJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO saveProduct(Long categoryId, ProductDTO productDTO){
        Category category = categoryJPARepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        Product product = modelMapper.map(productDTO, Product.class);
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

    public ProductResponse displayProductByCategoryID(Long categoryId){
        Category category = categoryJPARepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        List<Product> productList = productJPARepository.findByCategoryOrderByProductPriceAsc(category);
        List<ProductDTO> productDTOS = productList.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDTOS(productDTOS);
        return productResponse;
    }

    public ProductResponse displayProductByKeyword(String keyword){

        List<Product> productList = productJPARepository.findByProductNameLikeIgnoreCase(keyword);
        List<ProductDTO> productDTOS = productList.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDTOS(productDTOS);
        return productResponse;
    }

    public ProductDTO updateProduct(ProductDTO productDTO, Long productId){
        Product product1 = productJPARepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        Product product = modelMapper.map(productDTO, Product.class);
        product1.setProductName(product.getProductName());
        product1.setProductDescription(product.getProductDescription());
        product1.setProductQuantity(product.getProductQuantity());
        product1.setDiscount(product.getDiscount());
        product1.setProductPrice(product.getProductPrice());
        product1 = productJPARepository.save(product1);
        return modelMapper.map(product1, ProductDTO.class);
    }

    public ProductDTO deleteProduct(Long productId){
        Product deleteProduct = productJPARepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));
        productJPARepository.deleteById(productId);
        return modelMapper.map(deleteProduct, ProductDTO.class);
    }

    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product product1 = productJPARepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        String path = "images/";
        String fileName = uploadImage(path, image);
        product1.setProductImage(fileName);
        Product product = productJPARepository.save(product1);
        return modelMapper.map(product, ProductDTO.class);
    }

    private String uploadImage(String path, MultipartFile file) throws IOException {
        String OriginalName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(OriginalName.substring(OriginalName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

}