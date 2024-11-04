package com.example.backend.model.request.frontend.account;

import lombok.Data;

@Data
public class UpdateAccountProfileRequest {
    private String fullName;
    private String phone;
    private String address;

    public UpdateAccountProfileRequest(String fullName, String phone, String address) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }
}
