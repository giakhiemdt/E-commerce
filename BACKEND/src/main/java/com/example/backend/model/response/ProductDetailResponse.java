package com.example.backend.model.response;

import lombok.Data;

@Data
public class ProductDetailResponse {
    private String sellerFullName;
    private String description;

    public ProductDetailResponse(String sellerFullName, String description) {
        this.sellerFullName = sellerFullName;
        this.description = description;
    }
}
