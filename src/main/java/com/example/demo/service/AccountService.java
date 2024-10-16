package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Role;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.RegisterRequest;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.repository.AccountRepository;
import com.example.demo.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String registerAccount(RegisterRequest registerRequest) {

        Optional<Account> existingAccount = accountRepository.findByUsername(registerRequest.getUsername());
        if (existingAccount.isPresent()) {
            return "Username is already exist!";
        }

        Optional<Account> existingEmail = accountRepository.findByEmail(registerRequest.getEmail());
        if (existingEmail.isPresent()) {
            return "Email is already exist!";
        }

        if (!registerRequest.getPassword().equals(registerRequest.getRePassword())) {
            return "Passwords do not match!";
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Account newAccount = new Account(registerRequest.getUsername(), registerRequest.getEmail(), encodedPassword, Role.CUSTOMER);
        accountRepository.save(newAccount);
        return "Account registered successfully!";
    }

    public LoginResponse loginAccount(LoginRequest loginRequest) {
        Optional<Account> existingAccount = accountRepository.findByUsername(loginRequest.getUsername());
        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();

            if (passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
                String token = jwtUtil.generateToken(loginRequest.getUsername());
                return new LoginResponse(account.getUsername(), account.getEmail(),
                        account.getRole(), token);
            }else {
                throw new RuntimeException("Invalid password!");
            }
        }else{
            throw new RuntimeException("Username not found!");
        }
    }


}
