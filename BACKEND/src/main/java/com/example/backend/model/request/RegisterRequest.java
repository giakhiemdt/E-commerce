package com.example.backend.model.request;

import lombok.Data;

@Data
public class RegisterRequest {
    String username;
    String password;
    String rePassword;
    String email;
}
