package com.example.backend.model.request;

import lombok.Data;

@Data
public class RegisterRequest {
    String username;
    String email;
    String password;
    String rePassword;
}
