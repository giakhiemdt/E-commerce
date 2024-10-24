package com.example.backend.controller;

import com.example.backend.entity.Account;
import com.example.backend.entity.Role;
import com.example.backend.model.request.frontend.AccEditRequest;
import com.example.backend.model.request.frontend.AccIsActiveRequest;
import com.example.backend.service.AccountService;
import com.example.backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4000")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/accounts")
    public ResponseEntity<Map<Role, List<Account>>> getAllAccounts(@RequestHeader("AuthenticationToken") String token) {
        if (tokenService.isValidToken(tokenService.trueToken(token)) && tokenService.isADMIN(tokenService.trueToken(token))) {
            return ResponseEntity.ok(accountService.findAllByRole());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/change-account-status")
    public ResponseEntity<Boolean> changeAccountStatus(@RequestHeader("AuthenticationToken") String token, @RequestBody AccIsActiveRequest accIsActiveRequest) {
        if (tokenService.isValidToken(tokenService.trueToken(token)) && tokenService.isADMIN(tokenService.trueToken(token))) {
            return ResponseEntity.ok(accountService.updateStatusById(accIsActiveRequest.getAccountId()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/edit-account")
    public ResponseEntity<Boolean> editAccount(@RequestHeader("AuthenticationToken") String token, @RequestBody AccEditRequest accEditRequest) throws Exception {
        if (tokenService.isValidToken(tokenService.trueToken(token)) && tokenService.isADMIN(tokenService.trueToken(token))) {
            accountService.updateAccInfoById(accEditRequest);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
