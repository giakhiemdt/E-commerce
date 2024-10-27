package com.example.backend.controller;

import com.example.backend.entity.Role;
import com.example.backend.model.request.frontend.admin.ChangeAccountInformationRequest;
import com.example.backend.model.request.frontend.ChangeAccountProfileRequest;
import com.example.backend.model.response.admin.AccountListResponse;
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

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts") // Đường dẫn của ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<Role, List<AccountListResponse>>> getAllAccounts() { // Admin chỉ có 1 nên dell cần kiểm tra nữa!!!
        return ResponseEntity.ok(accountService.findAllByRole());
    }

    @PostMapping("/change-account-information") // Cũng của ADMIN!
    @PreAuthorize("hasAuthority('ADMMIN')")
    public ResponseEntity<Boolean> editAccount(@RequestBody ChangeAccountInformationRequest changeAccountInformationRequest) throws Exception {
        accountService.updateAccInfoById(changeAccountInformationRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/update-profile") // Của USẺ và SELLER
    @PreAuthorize("hasAnyAuthority('USER', 'SELLER')")
    public ResponseEntity<Boolean> updateProfile(@RequestBody ChangeAccountProfileRequest changeAccountProfileRequest) {
        accountService.updateProfile(changeAccountProfileRequest);
        return ResponseEntity.ok().build();
    }

}
