package com.example.backend.model.response.seller;

import lombok.Data;

@Data
public class SellerProfileResponse {
    private String fullName;
    private String phone;
    private String address;

    public SellerProfileResponse(String fullName, String phone, String address) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }
}
