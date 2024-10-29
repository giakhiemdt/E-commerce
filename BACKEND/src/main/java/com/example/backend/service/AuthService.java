package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.entity.Role;
import com.example.backend.model.request.frontend.LoginRequest;
import com.example.backend.model.request.frontend.RegisterRequest;
import com.example.backend.model.response.LoginResponse;
import com.example.backend.repository.AccountRepository;
import com.example.backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Boolean registerAccount(RegisterRequest registerRequest) {

        Optional<Account> existingAccount = accountService.getAccountByUserName(registerRequest.getUsername());
        if (existingAccount.isPresent()) { // Trùng tên rồi ní ơi
            return false;
        }

        Optional<Account> existingEmail = accountService.getAccountByEmail(registerRequest.getEmail());
        if (existingEmail.isPresent()) { // Trùng email nè ní
            return false;
        }

        if (!registerRequest.getPassword().equals(registerRequest.getRePassword())) { // Mật khẩu đell giống nhau
            return false;
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Account newAccount = new Account(registerRequest.getUsername(), registerRequest.getEmail(), encodedPassword, Role.USER);
        accountService.saveAccount(newAccount);
        return true;
    }

    public LoginResponse loginAccount(LoginRequest loginRequest) {
        Optional<Account> existingAccount = accountService.getAccountByUserName(loginRequest.getUsername());
        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();

            if (!account.isActive()) {
                throw new RuntimeException("Account is baned!");
            }

            if (passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
                String token = jwtUtil.generateToken(account, account.getRole());

                return new LoginResponse(account.getUsername(), token);
            }else {
                throw new RuntimeException("Invalid password!");
            }
        }else{
            throw new RuntimeException("Username not found!");
        }
    }

    public void logoutAccount() {
        tokenService.handleLogout();
    }
}
