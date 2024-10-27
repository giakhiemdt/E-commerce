package com.example.backend.model.request.frontend;

import lombok.Data;

@Data
public class ChangeAccountProfileRequest {
    private String fullname;
    private String phone;
    private String address;
}
