package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.Exception.APIException;
import com.ecommerce.Ecommerce.Exception.ResourceNotFoundException;
import com.ecommerce.Ecommerce.Model.Category;
import com.ecommerce.Ecommerce.Model.Product;
import com.ecommerce.Ecommerce.Payload.ProductDTO;
import com.ecommerce.Ecommerce.Payload.ProductResponse;
import com.ecommerce.Ecommerce.Repository.CategoryJPARepository;
import com.ecommerce.Ecommerce.Repository.ProductJPARepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ServiceProductImplementation implements ProductService {
    @Autowired
    private CategoryJPARepository categoryJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("{project.image}")
    private String path;

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

    public ProductResponse displayProduct(int pageNumber, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> pageablePage = productJPARepository.findAll(pageable);

        List<Product> productList = pageablePage.getContent();
        if(productList.isEmpty()){
            throw new APIException("Products Not Found");
        }
        List<ProductDTO> productDTOS = productList.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDTOS(productDTOS);
        productResponse.setPageNumber(pageablePage.getNumber());
        productResponse.setPageSize(pageablePage.getSize());
        productResponse.setTotalPages(pageablePage.getTotalPages());
        productResponse.setLastPage(pageablePage.isLast());
        return productResponse;
    }

    public ProductResponse displayProductByCategoryID(Long categoryId, int pageNumber, int pageSize, String sortBy, String sortDir){
        Category category = categoryJPARepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> pageablePage = productJPARepository.findByCategoryOrderByProductPriceAsc(pageable, category);

       List<ProductDTO> productDTOS = pageablePage.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDTOS(productDTOS);
        productResponse.setPageNumber(pageablePage.getNumber());
        productResponse.setPageSize(pageablePage.getSize());
        productResponse.setTotalPages(pageablePage.getTotalPages());
        productResponse.setLastPage(pageablePage.isLast());
        return productResponse;
    }

    public ProductResponse displayProductByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> pageablePage = productJPARepository.findByProductNameLikeIgnoreCase(pageable, keyword);

       if(pageablePage.isEmpty()){
            throw new APIException("Keyword Not Found");
        }
        List<ProductDTO> productDTOS = pageablePage.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDTOS(productDTOS);
        productResponse.setPageNumber(pageablePage.getNumber());
        productResponse.setPageSize(pageablePage.getSize());
        productResponse.setTotalPages(pageablePage.getTotalPages());
        productResponse.setLastPage(pageablePage.isLast());
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

        String fileName = fileService.uploadImage(path, image);
        product1.setProductImage(fileName);
        Product product = productJPARepository.save(product1);
        return modelMapper.map(product, ProductDTO.class);
    }

}