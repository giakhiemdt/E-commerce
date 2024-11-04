package com.example.backend.controller;

import com.example.backend.model.request.frontend.LoginRequest;
import com.example.backend.model.request.frontend.RegisterRequest;
import com.example.backend.model.response.LoginResponse;
import com.example.backend.model.response.StatusResponse;
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
    public ResponseEntity<StatusResponse> handleRegister(@RequestBody() RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.registerAccount(registerRequest));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> handleLogin(@RequestBody() LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.loginAccount(loginRequest));
    }

    @DeleteMapping(value = "/logout") // Xóa token trong white list nếu có!
    public ResponseEntity<StatusResponse> handleLogout() {
        return ResponseEntity.ok(authService.logoutAccount());
    }

}
