package com.example.backend.model.response.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductListResponse {
    private List<ProductResponse> productList;
    public ProductListResponse(List<ProductResponse> productList) {
        this.productList = productList;
    }
}
