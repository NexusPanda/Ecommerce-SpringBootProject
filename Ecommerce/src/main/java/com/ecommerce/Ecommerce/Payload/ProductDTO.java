package com.ecommerce.Ecommerce.Payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long productId;
    private String productName;
    private String productImage;
    private String productDescription;
    private Integer productQuantity;
    private double productPrice;
    private double discount;
    private double specialPrice;
}
