package com.example.backend.model.response;

import com.example.backend.entity.Role;
import lombok.Data;

@Data
public class LoginResponse {
    String username;
    String email;
    Role role;
    String token;

    public LoginResponse(String username, String email, Role role, String token) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.token = token;
    }
}
