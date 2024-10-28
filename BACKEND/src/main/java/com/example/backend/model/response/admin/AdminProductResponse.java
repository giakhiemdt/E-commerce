package com.example.backend.model.response.admin;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AdminProductResponse {
    private long id;
    private String name;
    private String productType;
    private long quantity;
    private boolean active;
    private long price;
    private String discount;
    private Timestamp postDate;
    private long sale;
    private String description;
    private String imageUrl;

    public AdminProductResponse(long id, String name, String productType, long quantity,
                                 boolean active, long price, String discount, Timestamp postDate, long sale, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.productType = productType;
        this.quantity = quantity;
        this.active = active;
        this.price = price;
        this.discount = discount;
        this.postDate = postDate;
        this.sale = sale;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
