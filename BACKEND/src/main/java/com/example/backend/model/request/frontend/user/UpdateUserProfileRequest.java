package com.example.backend.model.request.frontend.user;

import lombok.Data;

@Data
public class UpdateUserProfileRequest {
    private String fullName;
    private String phone;
    private String address;

    public UpdateUserProfileRequest(String fullName, String phone, String address) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }
}
