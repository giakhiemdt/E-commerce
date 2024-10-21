package com.example.backend.model.response;

import com.example.backend.entity.Role;

public class AccountEditResponse {
    Long id;
    String username;
    String email;
    String password;
    Role role;

    public AccountEditResponse(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
