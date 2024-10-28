package com.example.backend.controller;

import com.example.backend.entity.Role;
import com.example.backend.model.request.frontend.admin.UpdateAccountInformationRequest;
import com.example.backend.model.request.frontend.seller.UpdateSellerProfileRequest;
import com.example.backend.model.request.frontend.user.UpdateUserProfileRequest;
import com.example.backend.model.response.admin.AdminAccountResponse;
import com.example.backend.model.response.AccountInformationResponse;
import com.example.backend.model.response.seller.SellerProfileResponse;
import com.example.backend.model.response.user.UserProfileResponse;
import com.example.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4000")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    // Ní này là lấy thông tin tài khoản cơ bản nà!
    // Nói thật chứ việc chia hai endpoint nhưng đều có chung 1 tác dụng làm t suy nghĩ lâu vcl!!
    // Nhưng nói chung t cũng làm biết viết cho nên gắn vô luôn đi!!! ¯\(°_o)/¯
    @GetMapping("/account")
    @PreAuthorize("hasAnyAuthority('USER', 'SELLER')")
    public ResponseEntity<AccountInformationResponse> getAccountInformation() {
        return ResponseEntity.ok(accountService.getAccountInformation());
    }

    // USERRRRRR!!!!!!!!!!!!!!!

    // Lấy thông tin bờ rồ file nè
    @GetMapping("/user/profile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserProfileResponse> getUserProfile() {
        return ResponseEntity.ok(accountService.getUserProfile());
    }

    //Người dùng cập nhật profile --- Mệt quá ní ơi! ಠ_ಠ
    @PostMapping("/user/update-profile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Boolean> updateUserProfile(@RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
        accountService.updateUserProfile(updateUserProfileRequest);
        return ResponseEntity.ok().build();
    }

    // SELLER RRRRRRRR (*^*)!!!

    //Tính gộp mà thấy gọp nói hơi lỏ thôi kệ cứ chia ra mốt tối ưu sau. ( ͡° ͜ʖ ͡°)
    @GetMapping("/seller/profile")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<SellerProfileResponse> getSellerProfile() {
        return ResponseEntity.ok(accountService.getSellerProfile());
    }

    //Update profile này cũng khá giống của User á nhưng mà là bắt buộc
    @PostMapping("/seller/update-profile")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<Boolean> updateSellerProfile(@RequestBody UpdateSellerProfileRequest updateSellerProfileRequest) {
        accountService.updateSellerProfile(updateSellerProfileRequest);
        return ResponseEntity.ok().build();
    }

    // ADMIN !!!!

    //Lấy danh sách tài khoản để quản lý nè!! 凸-_-凸
    @GetMapping("/admin/accounts") // Đường dẫn của ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<Role, List<AdminAccountResponse>>> getAllAccounts() { // Admin chỉ có 1 nên dell cần kiểm tra nữa!!!
        return ResponseEntity.ok(accountService.findAllByRole());
    }

    @PostMapping("/admin/update-account") // Cũng của ADMIN!
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Boolean> editAccount(@RequestBody UpdateAccountInformationRequest updateAccountInformationRequest) {
        accountService.updateAccountInformation(updateAccountInformationRequest);
        return ResponseEntity.ok().build();
    }



}
