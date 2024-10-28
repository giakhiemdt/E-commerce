package com.example.backend.model.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AccountInformationResponse {
    private String username;
    private String email;
    private Timestamp createdDate;
    public AccountInformationResponse(String username, String email, Timestamp createdDate) {
        this.username = username;
        this.email = email;
        this.createdDate = createdDate;
    }
}
