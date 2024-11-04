package com.example.backend.model.response.account;

import com.example.backend.entity.enums.RoleEnum;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AccountResponse {
    private long accountId;
    private String username;
    private String email;
    private RoleEnum role;
    private Timestamp createdDate;
    private boolean isActive;
    public AccountResponse(String username, String email, Timestamp createdDate) {
        this.username = username;
        this.email = email;
        this.createdDate = createdDate;
    }
    public AccountResponse(long accountId, String username, String email, RoleEnum role, Timestamp createdDate, boolean isActive) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdDate = createdDate;
        this.isActive = isActive;
    }
}
