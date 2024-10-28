package com.example.backend.model.response;

import lombok.Data;

@Data
public class ProductsResponse {
    private long id;
    private String name;
    private String productType;
    private long price;
    private String discount;
    private String imageUrl;

    public ProductsResponse(long id, String name, String productType, long price, String discount, String imageUrl) {
        this.id = id;
        this.name = name;
        this.productType = productType;
        this.price = price;
        this.discount = discount;
        this.imageUrl = imageUrl;
    }
}


