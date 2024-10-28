package com.example.backend.model.response.user;

import lombok.Data;

@Data
public class UserProfileResponse {
    private String fullName;
    private String phone;
    private String address;

    public UserProfileResponse(String fullName, String phone, String address) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }
}
