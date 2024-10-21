package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.entity.Role;
import com.example.backend.model.request.AccEditRequest;
import com.example.backend.model.request.LoginRequest;
import com.example.backend.model.request.RegisterRequest;
import com.example.backend.model.response.LoginResponse;
import com.example.backend.repository.AccountRepository;
import com.example.backend.utility.JwtUtil;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> findByUserName(String username) {
        return accountRepository.findByUsername(username);
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public List<Account> findByRole(Role role) {
        return accountRepository.findByRole(role);
    }

    public Map<Role, List<Account>> findAllByRole() {
        Map<Role, List<Account>> map = new HashMap<>();
        map.put(Role.ADMIN, findByRole(Role.ADMIN));
        map.put(Role.SELLER, findByRole(Role.SELLER));
        map.put(Role.USER, findByRole(Role.USER));
        return map;
    }

    @Transactional
    public boolean updateStatusById(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            Account ac = account.get();
            accountRepository.updateAccountStatusByAccountId(accountId, !ac.isActive());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateUserNameById(Long accountId, String username) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            accountRepository.updateAccountUsernameByAccountId(accountId, username);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateEmailById(Long accountId, String email) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            accountRepository.updateAccountEmailByAccountId(accountId, email);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateRoleById(Long accountId, Role role) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            accountRepository.updateAccountRoleByAccountId(accountId, role);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateAccInfoById(AccEditRequest accEditRequest) {
        if (accEditRequest.getUsername() != null) {
            updateUserNameById(accEditRequest.getAccountId(), accEditRequest.getUsername());
        }
        if (accEditRequest.getEmail() != null) {
            updateEmailById(accEditRequest.getAccountId(), accEditRequest.getEmail());
        }
        if (accEditRequest.getRole() != null) {
            updateRoleById(accEditRequest.getAccountId(), accEditRequest.getRole());
        }
        return true;
    }

    //tạo Account đồng thời tạo thêm User
    public String registerAccount(RegisterRequest registerRequest) {

        Optional<Account> existingAccount = findByUserName(registerRequest.getUsername());
        if (existingAccount.isPresent()) {
            return "Username is already exist!";
        }

        Optional<Account> existingEmail = findByEmail(registerRequest.getEmail());
        if (existingEmail.isPresent()) {
            return "Email is already exist!";
        }

        if (!registerRequest.getPassword().equals(registerRequest.getRePassword())) {
            return "Passwords do not match!";
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Account newAccount = new Account(registerRequest.getUsername(), registerRequest.getEmail(), encodedPassword, Role.USER);
        accountRepository.save(newAccount);
        userService.createUser(newAccount);

        return "Account registered successfully!";
    }

    public LoginResponse loginAccount(LoginRequest loginRequest) {
        Optional<Account> existingAccount = findByUserName(loginRequest.getUsername());
        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();

            if (!account.isActive()) {
                throw new RuntimeException("Account is baned!");
            }

            if (passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
                String token = jwtUtil.generateToken(loginRequest.getUsername(), account.getRole());
                return new LoginResponse(account.getUsername(), token);
            }else {
                throw new RuntimeException("Invalid password!");
            }
        }else{
            throw new RuntimeException("Username not found!");
        }
    }


}
