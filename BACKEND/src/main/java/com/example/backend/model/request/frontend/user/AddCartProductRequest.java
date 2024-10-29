package com.example.backend.model.request.frontend.user;

import lombok.Data;

@Data
public class AddCartProductRequest {
    private long id;
    private int quantity;

}
