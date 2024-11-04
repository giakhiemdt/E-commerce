package com.example.backend.model.request.backend;

import com.example.backend.entity.enums.RoleEnum;
import lombok.Data;

@Data
public class RoleUpdateMessage {
    private String username;
    private RoleEnum roleEnum;

    public RoleUpdateMessage(String username, RoleEnum roleEnum) {
        this.username = username;
        this.roleEnum = roleEnum;
    }
}
