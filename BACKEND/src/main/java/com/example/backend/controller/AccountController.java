package com.example.backend.controller;

import com.example.backend.model.request.frontend.account.UpdateAccountRequest;
import com.example.backend.model.request.frontend.account.UpdateAccountProfileRequest;
import com.example.backend.model.response.StatusResponse;
import com.example.backend.model.response.account.*;
import com.example.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4000")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // USERRRRRR AND SELLERRR(*^*)!!!!!!!!!!!!!!!

    // Ní này là lấy thông tin tài khoản cơ bản nà!
    // Nói thật chứ việc chia hai endpoint nhưng đều có chung 1 tác dụng làm t suy nghĩ lâu vcl!!
    // Nhưng nói chung t cũng làm biết viết cho nên gắn vô luôn đi!!! ¯\(°_o)/¯
    @GetMapping("/account")
    @PreAuthorize("hasAnyAuthority('USER', 'SELLER', 'ADMIN')")
    public ResponseEntity<AccountResponse> getAccountInformation() {
        return ResponseEntity.ok(accountService.getAccountInformation());
    }

    // Lấy thông tin bờ rồ file nè
    @GetMapping("/profile")
    @PreAuthorize("hasAnyAuthority('USER', 'SELLER')")
    public ResponseEntity<AccountProfileResponse> getAccountProfile() {
        return ResponseEntity.ok(accountService.getAccountProfile());
    }

    //Người dùng cập nhật profile --- Mệt quá ní ơi! ಠ_ಠ
    @PutMapping("/profile")
    @PreAuthorize("hasAnyAuthority('USER', 'SELLER')")
    public ResponseEntity<StatusResponse> updateAccountProfile(@RequestBody UpdateAccountProfileRequest request) {
        return ResponseEntity.ok(accountService.updateAccountProfile(request));
    }

    // ADMIN !!!!

    //Lấy danh sách tài khoản để quản lý nè!! 凸-_-凸
    @GetMapping("/accounts") // Đường dẫn của ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AccountListWithRoleResponse> getAllAccounts() { // Admin chỉ có 1 nên dell cần kiểm tra nữa!!!
        return ResponseEntity.ok(accountService.findAllByRole());
    }

    @PutMapping("/update-account/{accountId}") // Cũng của ADMIN!
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StatusResponse> updateAccount(@PathVariable long accountId, @RequestBody UpdateAccountRequest request) {
        return ResponseEntity.ok(accountService.updateAccountInformation(accountId, request));
    }

}
