package com.example.backend.model.response.product;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProductListWithTypeResponse {
    private Map<String, List<ProductResponse>> products;
    public ProductListWithTypeResponse(Map<String, List<ProductResponse>> products) {
        this.products = products;
    }
}
