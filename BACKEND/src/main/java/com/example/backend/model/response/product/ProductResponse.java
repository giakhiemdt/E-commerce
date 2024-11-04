package com.example.backend.model.response.product;

import lombok.Data;

@Data
public class ProductResponse {
    private long id;
    private String name;
    private String productType;
    private long price;
    private int discount;
    private String imageUrl;

    public ProductResponse(long id, String name, String productType, long price, int discount, String imageUrl) {
        this.id = id;
        this.name = name;
        this.productType = productType;
        this.price = price;
        this.discount = discount;
        this.imageUrl = imageUrl;
    }
}


