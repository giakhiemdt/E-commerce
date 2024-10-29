package com.example.backend.model.response.seller;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SellerOrderResponse {
    private long productId;
    private int quantity;
    private String userName;
    private Timestamp orderDate;
}
