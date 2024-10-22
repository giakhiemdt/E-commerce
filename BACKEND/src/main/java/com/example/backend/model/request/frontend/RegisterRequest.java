package com.example.backend.model.request.frontend;

import lombok.Data;

@Data
public class RegisterRequest {
    String username;
    String email;
    String password;
    String rePassword;
}
