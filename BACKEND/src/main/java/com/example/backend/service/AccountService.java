package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.entity.Role;
import com.example.backend.model.request.frontend.AccEditRequest;
import com.example.backend.model.request.frontend.AccProfileRequest;
import com.example.backend.model.request.frontend.LoginRequest;
import com.example.backend.model.request.frontend.RegisterRequest;
import com.example.backend.model.response.AccountEntitiesResponse;
import com.example.backend.model.response.LoginResponse;
import com.example.backend.repository.AccountRepository;
import com.example.backend.utility.JwtUtil;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private MessageService messageService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SellerService sellerService;

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

    public List<AccountEntitiesResponse> findByRole(Role role) {
        List<Account> accounts = accountRepository.findByRole(role);
        List<AccountEntitiesResponse> responses = new ArrayList<>();
        for (Account account : accounts) {
            responses.add(new AccountEntitiesResponse(
                    account.getId(), account.getUsername(),
                    account.getEmail(), account.getRole(),
                    account.getCreatedDate(), account.isActive()));
        }
        return responses;
    }

    public Optional<Account> findByToken(String token) {
        return findByUserName(jwtUtil.extractName(tokenService.trueToken(token)));
    }

    public Map<Role, List<AccountEntitiesResponse>> findAllByRole() {
        Map<Role, List<AccountEntitiesResponse>> map = new HashMap<>();
        map.put(Role.ADMIN, findByRole(Role.ADMIN));
        map.put(Role.SELLER, findByRole(Role.SELLER));
        map.put(Role.USER, findByRole(Role.USER));
        return map;
    }

    @Transactional
    public boolean updateStatusById(String authHeader,Long accountId) {
        String token = tokenService.trueToken(authHeader);
        if (tokenService.isValidToken(token) && tokenService.isADMIN(token)) {
            Optional<Account> existingAccount = accountRepository.findById(accountId);
            if (existingAccount.isPresent()) {
                Account account = existingAccount.get();
                accountRepository.updateAccountStatusByAccountId(accountId, !account.isActive());
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void updateUserNameById(Long accountId, String username) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            accountRepository.updateAccountUsernameByAccountId(accountId, username);
        }
    }

    @Transactional
    public void updateEmailById(Long accountId, String email) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            accountRepository.updateAccountEmailByAccountId(accountId, email);
        }
    }

    @Transactional
    public void updateRoleById(Long accountId, Role role) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            accountRepository.updateAccountRoleByAccountId(accountId, role);
        }
    }

    @Transactional
    public boolean updateAccInfoById(String authHeader, AccEditRequest accEditRequest) {
        String token = tokenService.trueToken(authHeader);
        if (tokenService.isValidToken(token) && tokenService.isADMIN(token)) {
            if (accEditRequest.getUsername() != null) {
                updateUserNameById(accEditRequest.getAccountId(), accEditRequest.getUsername());
            }
            if (accEditRequest.getEmail() != null) {
                updateEmailById(accEditRequest.getAccountId(), accEditRequest.getEmail());
            }
            if (accEditRequest.getRole() != null) {
                updateRoleById(accEditRequest.getAccountId(), accEditRequest.getRole());
                if (accEditRequest.getRole() == Role.SELLER) {
                    Optional<Account> existingAccount = accountRepository.findById(accEditRequest.getAccountId());
                    if (existingAccount.isPresent()) {
                        Account account = existingAccount.get();
                        messageService.sendUpdateRoleSellerMessage(account.getUsername());
                    }
                }
            }
            return true;
        }
        return false;
    }

    //tạo Account đồng thời tạo thêm User nè
    public Boolean registerAccount(RegisterRequest registerRequest) {

        Optional<Account> existingAccount = findByUserName(registerRequest.getUsername());
        if (existingAccount.isPresent()) { // Trùng tên rồi ní ơi
            return false;
        }

        Optional<Account> existingEmail = findByEmail(registerRequest.getEmail());
        if (existingEmail.isPresent()) { // Trùng email nè ní
            return false;
        }

        if (!registerRequest.getPassword().equals(registerRequest.getRePassword())) { // Mật khẩu đell giống nhau
            return false;
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Account newAccount = new Account(registerRequest.getUsername(), registerRequest.getEmail(), encodedPassword, Role.USER);
        accountRepository.save(newAccount);
        userService.createUser(newAccount);

        return true;
    }

    public LoginResponse loginAccount(LoginRequest loginRequest) {
        Optional<Account> existingAccount = findByUserName(loginRequest.getUsername());
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

    public boolean logoutAccount(String authHeader) {
        return tokenService.handleLogout(authHeader);
    }

    public boolean updateProfile(String authHeader, AccProfileRequest accProfileRequest) { // muốn đổi username email thì phải liên hệ admin đổi còn profile thì có thể tự sửa
        String token = tokenService.trueToken(authHeader);
        Optional<Account> existingAccount = findByToken(token);
        if (tokenService.isValidToken(token) && existingAccount.isPresent()) {
            if (tokenService.isUSER(token)) {
                // Chừa chỗ cho UẺ sau này update!
            }else if (tokenService.isSELLER(token)) {
                sellerService.updateProfile( existingAccount.get(), accProfileRequest);
            } // Admin đell có profile nên không cần đổi
            return true;
        }
        return false;
    }
}
