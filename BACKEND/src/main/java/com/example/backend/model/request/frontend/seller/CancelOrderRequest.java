package com.example.backend.model.request.frontend.seller;

import lombok.Data;

@Data
public class CancelOrderRequest {
    private long orderId;
}
