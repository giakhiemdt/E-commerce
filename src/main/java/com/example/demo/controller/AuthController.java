package com.example.demo.controller;

import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.RegisterRequest;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.service.AccountService;
import com.example.demo.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> handleRegister(@RequestBody() RegisterRequest registerRequest) {
        return ResponseEntity.ok(accountService.registerAccount(registerRequest));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> handleLogin(@RequestBody() LoginRequest loginRequest) {
        return ResponseEntity.ok(accountService.loginAccount(loginRequest));
    }


}
