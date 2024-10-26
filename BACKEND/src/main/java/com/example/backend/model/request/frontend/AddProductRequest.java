package com.example.backend.model.request.frontend;

import lombok.Data;

@Data
public class AddProductRequest {
    long typeId;
    String name;
    int quantity;
    int price;
    String description;
    String imageUrl;
}
