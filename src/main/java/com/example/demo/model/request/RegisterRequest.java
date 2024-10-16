package com.example.demo.model.request;

import lombok.Data;

@Data
public class RegisterRequest {
    String username;
    String password;
    String rePassword;
    String email;
}
