package com.example.backend.model.request.frontend.account;

import com.example.backend.entity.enums.RoleEnum;
import lombok.Data;

@Data
public class UpdateAccountRequest {
    private String username;
    private String email;
    private RoleEnum roleEnum;
    private boolean active;
}

