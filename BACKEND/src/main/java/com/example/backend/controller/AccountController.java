package com.example.backend.controller;

import com.example.backend.entity.Account;
import com.example.backend.entity.Role;
import com.example.backend.model.request.frontend.AccEditRequest;
import com.example.backend.model.request.frontend.AccIsActiveRequest;
import com.example.backend.model.request.frontend.AccProfileRequest;
import com.example.backend.model.response.AccountEntitiesResponse;
import com.example.backend.service.AccountService;
import com.example.backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4000")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/accounts") // Đường dẫn của ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<Role, List<AccountEntitiesResponse>>> getAllAccounts() { // Admin chỉ có 1 nên dell cần kiểm tra nữa!!!
        return ResponseEntity.ok(accountService.findAllByRole());
    }

    @PostMapping("/change-account-status") // Đường dẫn của ADMIN luôn nè
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Boolean> changeAccountStatus(@RequestHeader("AuthenticationToken") String token, @RequestBody AccIsActiveRequest accIsActiveRequest) {
        if (accountService.updateStatusById(token, accIsActiveRequest.getAccountId())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/edit-account") // Cũng của ADMIN!
    public ResponseEntity<Boolean> editAccount(@RequestHeader("AuthenticationToken") String token, @RequestBody AccEditRequest accEditRequest) throws Exception {
        if (accountService.updateAccInfoById(token ,accEditRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/update-profile") // Của USẺ và SELLER
    public ResponseEntity<Boolean> updateProfile(@RequestHeader("AuthenticationToken") String token, @RequestBody AccProfileRequest accProfileRequest) {
        if (accountService.updateProfile(token, accProfileRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
