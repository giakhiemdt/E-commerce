package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.entity.Role;
import com.example.backend.model.request.frontend.admin.UpdateAccountInformationRequest;
import com.example.backend.model.request.frontend.LoginRequest;
import com.example.backend.model.request.frontend.RegisterRequest;
import com.example.backend.model.request.frontend.seller.UpdateSellerProfileRequest;
import com.example.backend.model.request.frontend.user.UpdateUserProfileRequest;
import com.example.backend.model.response.admin.AdminAccountResponse;
import com.example.backend.model.response.LoginResponse;
import com.example.backend.model.response.AccountInformationResponse;
import com.example.backend.model.response.seller.SellerProfileResponse;
import com.example.backend.model.response.user.UserProfileResponse;
import com.example.backend.repository.AccountRepository;
import com.example.backend.utility.JwtUtil;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final MessageService messageService;
    private final TokenService tokenService;
    private final SellerService sellerService;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil,
                          UserService userService,
                          MessageService messageService,
                          TokenService tokenService,
                          SellerService sellerService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.messageService = messageService;
        this.tokenService = tokenService;
        this.sellerService = sellerService;
    }

    public Optional<Account> getAccountByUserName(String username) {
        return accountRepository.findByUsername(username);
    }

    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public boolean isActive(long id) {
        return accountRepository.findById(id)
                .map(Account::isActive)
                .orElse(false);
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

    // AUTHENTICATION ở đây!

    //tạo Account đồng thời tạo thêm User nè
    public Boolean registerAccount(RegisterRequest registerRequest) {

        Optional<Account> existingAccount = getAccountByUserName(registerRequest.getUsername());
        if (existingAccount.isPresent()) { // Trùng tên rồi ní ơi
            return false;
        }

        Optional<Account> existingEmail = getAccountByEmail(registerRequest.getEmail());
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
        Optional<Account> existingAccount = getAccountByUserName(loginRequest.getUsername());
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


    // Dùng chung USER với SELLER!!!

    //Lấy tài khoản người dùng nà ~~
    // User với Seller xài chung nha!
    public AccountInformationResponse getAccountInformation() {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> existingAccount = getAccountByUserName(username);
        return existingAccount.map(account -> new AccountInformationResponse(username,
                account.getEmail(),
                account.getCreatedDate())).orElse(null);
    }

    // USER AAAAA!!!!

    // Lấy bờ rồ file nữa nè --- mệt vl!!
    public UserProfileResponse getUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> existingAccount = getAccountByUserName(username);
        return existingAccount.map(userService::getUserProfile).orElse(null);
    }

    //Cập nhật thông tin mới cho profile nè!
    @Transactional
    public void updateUserProfile(UpdateUserProfileRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> existingAccount = getAccountByUserName(username);
        existingAccount.ifPresent(account ->
                userService.updateUserProfile(request, account));
    }

    // SELLELLERRRRR

    // Copy thằng trên nhưng đại khái là nó dùng để lấy pro file Seller á!
    public SellerProfileResponse getSellerProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> existingAccount = getAccountByUserName(username);
        return existingAccount.map(sellerService::getSellerProfile).orElse(null);
    }

    // Copy thằng trên của thằng trên nữa nè
    // Cập nhật profile cho seller
    public void updateSellerProfile(UpdateSellerProfileRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> existingAccount = getAccountByUserName(username);
        existingAccount.ifPresent(account ->
                sellerService.updateSellerProfile(request, account));

    }

    // AAADMIN!!

    // Hai thằng này chung là lấy account nha!!!
    public List<AdminAccountResponse> findByRole(Role role) {
        List<Account> accounts = accountRepository.findByRole(role);
        return accounts.stream().map(account -> new AdminAccountResponse(
                account.getId(), account.getUsername(),
                account.getEmail(), account.getRole(),
                account.getCreatedDate(), account.isActive())
        ).toList();
    }
    // Thằng này nữa nè!!
    public Map<Role, List<AdminAccountResponse>> findAllByRole() {
        Map<Role, List<AdminAccountResponse>> map = new HashMap<>();
        map.put(Role.ADMIN, findByRole(Role.ADMIN));
        map.put(Role.SELLER, findByRole(Role.SELLER));
        map.put(Role.USER, findByRole(Role.USER));
        return map;
    }

    @Transactional
    public void updateAccountInformation(UpdateAccountInformationRequest request) {
        if (request.isActive() != isActive(request.getAccountId())) { // Nói chứ t cũng không biết tại sao mình lại đặt cái này ở đầu nữa ¯\_(ツ)_/¯
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

}
