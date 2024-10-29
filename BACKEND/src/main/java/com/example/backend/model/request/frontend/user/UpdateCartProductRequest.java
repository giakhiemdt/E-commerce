package com.example.backend.model.request.frontend.user;

import lombok.Data;

@Data
public class UpdateCartProductRequest {
    private long productId;
    private int quantity;
}
