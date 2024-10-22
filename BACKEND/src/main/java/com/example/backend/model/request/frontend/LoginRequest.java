package com.example.backend.model.request.frontend;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}
