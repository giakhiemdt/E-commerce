package com.example.backend.model.response.product;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SellerProductDetailResponse {

    private int quantity;
    private boolean isActive;
    private Timestamp postedDate;
    private int sale;
    private String productDescription;
    public SellerProductDetailResponse(int quantity, boolean isActive, Timestamp postedDate, int sale, String productDescription) {
        this.quantity = quantity;
        this.isActive = isActive;
        this.postedDate = postedDate;
        this.sale = sale;
        this.productDescription = productDescription;
    }
}
