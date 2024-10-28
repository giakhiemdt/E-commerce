package com.example.backend.model.request.frontend.seller;

import lombok.Data;

@Data
public class AddProductRequest {
    private long typeId;
    private String name;
    private int quantity;
    private int price;
    private String description;
    private String imageUrl;
}
