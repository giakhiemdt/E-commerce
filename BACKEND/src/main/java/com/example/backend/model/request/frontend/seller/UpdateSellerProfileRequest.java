package com.example.backend.model.request.frontend.seller;

import lombok.Data;

@Data
public class UpdateSellerProfileRequest {
    private String fullName;
    private String phone;
    private String address;

    public UpdateSellerProfileRequest(String fullName, String phone, String address) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }
}
