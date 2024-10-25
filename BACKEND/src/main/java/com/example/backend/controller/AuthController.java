package com.example.backend.controller;

import com.example.backend.model.request.frontend.LoginRequest;
import com.example.backend.model.request.frontend.RegisterRequest;
import com.example.backend.model.response.LoginResponse;
import com.example.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4000")
public class AuthController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> handleRegister(@RequestBody() RegisterRequest registerRequest) {
        if (accountService.registerAccount(registerRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> handleLogin(@RequestBody() LoginRequest loginRequest) {
        LoginResponse loginResponse = accountService.loginAccount(loginRequest);
        if (loginResponse != null) {
            return ResponseEntity.ok(loginResponse);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Boolean> handleLogout(@RequestHeader("AuthenticationToken") String token) {
        System.out.println("Nihaoma");
        if (accountService.logoutAccount(token)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
