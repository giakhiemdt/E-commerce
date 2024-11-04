package com.example.backend.model.response.account;

import com.example.backend.entity.enums.RoleEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AccountListWithRoleResponse {
    Map<RoleEnum, List<AccountResponse>> accounts;
    public AccountListWithRoleResponse(Map<RoleEnum, List<AccountResponse>> accounts) {
        this.accounts = accounts;
    }
}
