package com.example.backend.model.request.frontend.seller;

import lombok.Data;

@Data
public class UpdateProductRequest {
    private long productId;
    private long typeId;
    private String name;
    private int quantity;
    private boolean active;
    private int price;
    private String discount;
    private String description;
    private String imageUrl;
}
