package com.example.backend.model.request;

import com.example.backend.entity.Role;
import lombok.Data;

@Data
public class AccEditRequest {
    Long accountId;
    String username;
    String email;
    Role role;
}
