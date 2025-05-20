package com.ecommerce.Ecommerce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "ProductTable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NotBlank
    @Size(min = 4, message = "Minimum 4 Character should contain in ProductName")
    private String productName;
    private String productImage;

    @NotBlank
    @Size(min = 6, message = "Minimum 6 Character should contain in Product Description")
    private String productDescription;
    private Integer productQuantity;
    private double productPrice;
    private double discount;
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "p_Id")
    private Category category;

}
