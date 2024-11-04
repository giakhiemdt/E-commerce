package com.example.backend.model.response.product;

import lombok.Data;

@Data
public class UserProductDetailResponse {
    private String sellerFullName;
    private String description;

    public UserProductDetailResponse(String sellerFullName, String description) {
        this.sellerFullName = sellerFullName;
        this.description = description;
    }
}
