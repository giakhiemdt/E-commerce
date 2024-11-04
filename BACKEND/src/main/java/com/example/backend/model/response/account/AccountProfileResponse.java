package com.example.backend.model.response.account;

import lombok.Data;

@Data
public class AccountProfileResponse {
    private String fullName;
    private String phone;
    private String address;

    public AccountProfileResponse(String fullName, String phone, String address) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }
}
