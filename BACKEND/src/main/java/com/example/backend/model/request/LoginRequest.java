package com.example.backend.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}
