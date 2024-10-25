package com.example.backend.controller;

import com.example.backend.entity.Account;
import com.example.backend.entity.Role;
import com.example.backend.model.request.frontend.AccEditRequest;
import com.example.backend.model.request.frontend.AccIsActiveRequest;
import com.example.backend.model.request.frontend.AccProfileRequest;
import com.example.backend.service.AccountService;
import com.example.backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<Role, List<Account>>> getAllAccounts(@RequestHeader("AuthenticationToken") String token) {
        Map<Role, List<Account>> response = accountService.findAllByRole(token);
        if (!response.isEmpty()) { // Trả về kiểu gì cũng có tài khoản ADMIN nên map đell bao giờ rỗng, rỗng là chỉ khi có thằng nào đó muốn lấy bất hợp pháp thôi!
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/change-account-status") // Đường dẫn của ADMIN luôn nè
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
