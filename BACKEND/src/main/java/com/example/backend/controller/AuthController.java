package com.example.backend.controller;

import com.example.backend.model.request.frontend.LoginRequest;
import com.example.backend.model.request.frontend.RegisterRequest;
import com.example.backend.model.response.LoginResponse;
import com.example.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4000")
public class AuthController {


    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> handleRegister(@RequestBody() RegisterRequest registerRequest) {
        if (authService.registerAccount(registerRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> handleLogin(@RequestBody() LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.loginAccount(loginRequest);
        if (loginResponse != null) {
            return ResponseEntity.ok(loginResponse);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Boolean> handleLogout() {
        authService.logoutAccount();
        return ResponseEntity.ok().build();
    }

}
