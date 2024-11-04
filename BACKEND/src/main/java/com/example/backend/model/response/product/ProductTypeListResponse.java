package com.example.backend.model.response.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductTypeListResponse {
    private List<ProductTypeResponse> productTypeList;
    public ProductTypeListResponse(List<ProductTypeResponse> productTypeList) {
        this.productTypeList = productTypeList;
    }
}
