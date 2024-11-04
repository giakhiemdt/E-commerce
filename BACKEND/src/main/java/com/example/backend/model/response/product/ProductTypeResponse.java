package com.example.backend.model.response.product;

import lombok.Data;

@Data
public class ProductTypeResponse {
    private long id;
    private String name;

    public ProductTypeResponse(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
