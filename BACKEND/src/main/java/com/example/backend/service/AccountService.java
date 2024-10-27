package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.entity.Role;
import com.example.backend.model.request.frontend.admin.ChangeAccountInformationRequest;
import com.example.backend.model.request.frontend.ChangeAccountProfileRequest;
import com.example.backend.model.request.frontend.LoginRequest;
import com.example.backend.model.request.frontend.RegisterRequest;
import com.example.backend.model.response.admin.AccountListResponse;
import com.example.backend.model.response.LoginResponse;
import com.example.backend.repository.AccountRepository;
import com.example.backend.utility.JwtUtil;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public boolean isActive(long id) {
        return accountRepository.findById(id)
                .map(Account::isActive)
                .orElse(false);
    }

    public List<AccountListResponse> findByRole(Role role) {
        List<Account> accounts = accountRepository.findByRole(role);
        List<AccountListResponse> responses = new ArrayList<>();
        for (Account account : accounts) {
            responses.add(new AccountListResponse(
                    account.getId(), account.getUsername(),
                    account.getEmail(), account.getRole(),
                    account.getCreatedDate(), account.isActive()));
        }
        return responses;
    }

    public Map<Role, List<AccountListResponse>> findAllByRole() {
        Map<Role, List<AccountListResponse>> map = new HashMap<>();
        map.put(Role.ADMIN, findByRole(Role.ADMIN));
        map.put(Role.SELLER, findByRole(Role.SELLER));
        map.put(Role.USER, findByRole(Role.USER));
        return map;
    }

    @Transactional
    public void updateStatusById(Long accountId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> existingAccount = accountRepository.findByUsername(username);
        existingAccount.ifPresent(account -> accountRepository.updateAccountStatusByAccountId(accountId, !account.isActive()));
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
    public void updateAccInfoById(ChangeAccountInformationRequest request) {
        if (request.isActive() != isActive(request.getAccountId())) {
            updateStatusById(request.getAccountId());
        }
        if (request.getUsername() != null) {
            updateUserNameById(request.getAccountId(), request.getUsername());
        }
        if (request.getEmail() != null) {
            updateEmailById(request.getAccountId(), request.getEmail());
        }
        if (request.getRole() != null) {
            updateRoleById(request.getAccountId(), request.getRole());
            if (request.getRole() == Role.SELLER) {
                Optional<Account> existingAccount = accountRepository.findById(request.getAccountId());
                existingAccount.ifPresent(account -> messageService.sendUpdateRoleSellerMessage(account.getUsername()));
            }
        }

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

    public void updateProfile(ChangeAccountProfileRequest changeAccountProfileRequest) { // muốn đổi username email thì phải liên hệ admin đổi còn profile thì có thể tự sửa
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        Optional<Account> existingAccount = findByUserName(username);

        if (existingAccount.isPresent()) {
            if (role.equals("USER")) {
                // Chừa chỗ cho UẺ sau này update!
            }else if (role.equals("SELLER")) {
                sellerService.updateProfile( existingAccount.get(), changeAccountProfileRequest);
            } // Admin đell có profile nên không cần đổi
        }
    }
}
