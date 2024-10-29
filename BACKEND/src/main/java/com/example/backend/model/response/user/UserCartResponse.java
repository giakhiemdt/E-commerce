package com.example.backend.model.response.user;

import com.example.backend.model.response.ProductResponse;
import lombok.Data;

import java.util.Map;

@Data
public class UserCartResponse {

    private String phone;
    private String adress;
    private Map<ProductResponse, Integer> cart;

    public UserCartResponse() {}

    public UserCartResponse(String phone, String adress, Map<ProductResponse, Integer> cart) {
        this.phone = phone;
        this.adress = adress;
        this.cart = cart;
    }
}
