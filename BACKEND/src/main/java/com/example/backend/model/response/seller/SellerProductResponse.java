package com.example.backend.model.response.seller;

import lombok.Data;

@Data
public class SellerProductResponse {
    private long id;
    private String name;
    private String productType;
    private long price;
    private String discount;
    private String imageUrl;
}
