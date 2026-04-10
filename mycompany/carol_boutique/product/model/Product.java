package com.mycompany.carol_boutique.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    int productId;
    String productName;
    Category category;
    long price;
    String description;
    ProductType prodType;

    public Product(String productName, Category category, long price, String description, ProductType prodType) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.prodType = prodType;
    }

    

}
