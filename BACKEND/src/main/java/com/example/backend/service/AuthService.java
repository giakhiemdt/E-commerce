package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.entity.enums.RoleEnum;
import com.example.backend.model.request.frontend.LoginRequest;
import com.example.backend.model.request.frontend.RegisterRequest;
import com.example.backend.model.response.LoginResponse;
import com.example.backend.model.response.StatusResponse;
import com.example.backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Autowired
    public AuthService(AccountService accountService,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, TokenService tokenService) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
    }

    // AUTHENTICATION ở đây!

    //tạo Account đồng thời tạo thêm User nè
    public StatusResponse registerAccount(RegisterRequest registerRequest) {

        Optional<Account> existingAccount = accountService.getAccountByUserName(registerRequest.getUsername());
        if (existingAccount.isPresent()) { // Trùng tên rồi ní ơi
            return new StatusResponse(false, "This username is already in use!");
        }

        Optional<Account> existingEmail = accountService.getAccountByEmail(registerRequest.getEmail());
        if (existingEmail.isPresent()) { // Trùng email nè ní
            return new StatusResponse(false, "This email is already in use!");
        }

        if (!registerRequest.getPassword().equals(registerRequest.getRePassword())) { // Mật khẩu đell giống nhau
            return new StatusResponse(false, "Passwords do not match!");
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Account newAccount = new Account(registerRequest.getUsername(), registerRequest.getEmail(), encodedPassword, RoleEnum.USER);
        accountService.saveAccount(newAccount);
        return new StatusResponse(true, "Account created!");
    }

    //Đăng nhập ở đây!!
    public LoginResponse loginAccount(LoginRequest loginRequest) {
        Optional<Account> existingAccount = accountService.getAccountByUserName(loginRequest.getUsername());
        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();

            if (!account.isActive()) {
                return new LoginResponse(false, "Account is banned!", null);
            }

            if (passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
                String token = jwtUtil.generateToken(account, account.getRoleEnum());

                return new LoginResponse(true, "Login success!", token);
            }else {
                return new LoginResponse(false, "Invalid password!", null);
            }
        }else{
            return new LoginResponse(false, "Username not found!", null);
        }
    }

    // ĐĂNG XUẤT NÈ!
    public StatusResponse logoutAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> existingAccount = accountService.getAccountByUserName(username);
        return tokenService.handleLogout(existingAccount);
    }
}
