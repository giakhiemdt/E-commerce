package com.example.backend.model.request.frontend.admin;

import com.example.backend.entity.Role;
import lombok.Data;

@Data
public class UpdateAccountInformationRequest {
    private Long accountId; // Id này là của tài khoản bị thay đổi chứ không phải của admin!!!! Chuyện quan trong phải nói ba lần!!!
    private String username;
    private String email;
    private Role role;
    private boolean active;
}

