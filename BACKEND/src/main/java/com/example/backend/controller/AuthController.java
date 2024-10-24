package com.example.backend.controller;

import com.example.backend.model.request.frontend.LoginRequest;
import com.example.backend.model.request.frontend.RegisterRequest;
import com.example.backend.model.response.LoginResponse;
import com.example.backend.service.AccountService;
import com.example.backend.service.TokenService;
import com.example.backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4000")
public class AuthController {

    @Autowired
    private AccountService accountService;

    @Autowired
    TokenService tokenService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> handleRegister(@RequestBody() RegisterRequest registerRequest) {
        return ResponseEntity.ok(accountService.registerAccount(registerRequest));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> handleLogin(@RequestBody() LoginRequest loginRequest) {
        return ResponseEntity.ok(accountService.loginAccount(loginRequest));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> handleLogout(@RequestHeader("AuthenticationToken") String token) {
        String result = tokenService.handleLogout(token);
        return ResponseEntity.ok(result);
    }

}
