package com.example.backend.model.response;

import com.example.backend.entity.Role;
import lombok.Data;

@Data
public class LoginResponse {
    String username; // Thực ra cũng không cần username cho lắm!
    String token;

    public LoginResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
