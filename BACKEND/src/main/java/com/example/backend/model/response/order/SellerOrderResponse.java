package com.example.backend.model.response.order;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SellerOrderResponse {
    private long productId;
    private int quantity;
    private String userName;
    private Timestamp orderDate;

    public SellerOrderResponse(long productId, int quantity, String userName, Timestamp orderDate) {
        this.productId = productId;
        this.quantity = quantity;
        this.userName = userName;
        this.orderDate = orderDate;
    }
}
