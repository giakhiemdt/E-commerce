package com.example.backend.model.request.backend;

import com.example.backend.entity.Role;
import lombok.Data;

@Data
public class RoleUpdateMessage {
    private String username;
    private Role role;

    public RoleUpdateMessage(String username, Role role) {
        this.username = username;
        this.role = role;
    }
}
