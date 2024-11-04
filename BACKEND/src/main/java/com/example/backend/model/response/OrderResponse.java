package com.example.backend.model.response;

import lombok.Data;

@Data
public class OrderResponse {
    private long id;
    private long productId;
    private int quantity;

    public OrderResponse(long id, long productId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }
}
