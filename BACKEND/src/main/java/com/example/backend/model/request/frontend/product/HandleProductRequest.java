package com.example.backend.model.request.frontend.product;

import lombok.Data;

@Data
public class HandleProductRequest {
    private String productName;
    private String productTypeName;
    private int quantity;
    private boolean isActive;
    private int discount;
    private long price;
    private String description;
    private String imageUrl;
}
