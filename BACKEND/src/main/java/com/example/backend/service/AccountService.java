package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.entity.enums.RoleEnum;
import com.example.backend.model.request.frontend.account.UpdateAccountRequest;
import com.example.backend.model.request.frontend.account.UpdateAccountProfileRequest;
import com.example.backend.model.response.StatusResponse;
import com.example.backend.model.response.account.*;
import com.example.backend.repository.AccountRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final MessageService messageService;
    private final SellerService sellerService;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          UserService userService,
                          MessageService messageService,
                          SellerService sellerService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.messageService = messageService;
        this.sellerService = sellerService;
    }

    public Optional<Account> getAccountById(long accountId) {
        return accountRepository.findById(accountId);
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
    public void updateStatusById(Long accountId, Account account) {
        accountRepository.updateAccountStatusByAccountId(accountId, !account.isActive());
    }

    @Transactional
    public void updateUserNameById(Long accountId, String username) {
        accountRepository.updateAccountUsernameByAccountId(accountId, username);
    }

    @Transactional
    public void updateEmailById(Long accountId, String email) {
        accountRepository.updateAccountEmailByAccountId(accountId, email);
    }

    @Transactional
    public void updateRoleById(Long accountId, RoleEnum roleEnum) {
        accountRepository.updateAccountRoleByAccountId(accountId, roleEnum);
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
        userService.createUser(account);
    }


    // Dùng chung USER với SELLER!!!

    //Lấy tài khoản người dùng nà ~~
    // User với Seller xài chung nha!
    public AccountResponse getAccountInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username  = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        Optional<Account> existingAccount = getAccountByUserName(username);
        if (role.equals("ADMIN")) {
            return existingAccount.map(account -> new AccountResponse(
                    account.getId(),
                    username,
                    account.getEmail(),
                    account.getRoleEnum(),
                    account.getCreatedDate(),
                    account.isActive()
            )).orElse(null);
        }
        return existingAccount.map(account -> new AccountResponse(username,
                account.getEmail(),
                account.getCreatedDate())).orElse(null);
    }

    // Lấy bờ rồ file nữa nè --- mệt vl!!
    public AccountProfileResponse getAccountProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> existingAccount = getAccountByUserName(authentication.getName());
        if (authentication.getAuthorities().iterator().next().getAuthority().equals("SELLER")) {
            return existingAccount.map(sellerService::getSellerProfile).orElse(null);
        }
        return existingAccount.map(userService::getUserProfile).orElse(null);
    }

    //Cập nhật thông tin mới cho profile nè!
    @Transactional
    public StatusResponse updateAccountProfile(UpdateAccountProfileRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        Optional<Account> existingAccount = getAccountByUserName(authentication.getName());
        if (existingAccount.isPresent()) {
            if (role.equals("USER")) {
                userService.updateUserProfile(request, existingAccount.get());
                return new StatusResponse(true, "User profile updated!");
            }else {
                sellerService.updateSellerProfile(request, existingAccount.get());
                return new StatusResponse(true, "Seller profile updated!");
            }
        }
        return new StatusResponse(false, "Invalid account!");
    }

    // AAADMIN!!

    // Hai thằng này chung là lấy account nha!!!
    public List<AccountResponse> findByRole(RoleEnum roleEnum) {
        List<Account> accounts = accountRepository.findByRoleEnum(roleEnum);
        return accounts.stream().map(account -> new AccountResponse(
                account.getId(), account.getUsername(),
                account.getEmail(), account.getRoleEnum(),
                account.getCreatedDate(), account.isActive())
        ).toList();
    }
    // Thằng này nữa nè!!
    public AccountListWithRoleResponse findAllByRole() {
        Map<RoleEnum, List<AccountResponse>> map = new HashMap<>();
        map.put(RoleEnum.ADMIN, findByRole(RoleEnum.ADMIN));
        map.put(RoleEnum.SELLER, findByRole(RoleEnum.SELLER));
        map.put(RoleEnum.USER, findByRole(RoleEnum.USER));
        return new AccountListWithRoleResponse(map);
    }

    @Transactional
    public StatusResponse updateAccountInformation(long accountId, UpdateAccountRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> existingAccount = accountRepository.findByUsername(username);
        if (existingAccount.isPresent()) {
            if (request.isActive() != isActive(accountId)) { // Nói chứ t cũng không biết tại sao mình lại đặt cái này ở đầu nữa ¯\_(ツ)_/¯
                updateStatusById(accountId, existingAccount.get());
            }
            if (request.getUsername() != null) {
                updateUserNameById(accountId, request.getUsername());
            }
            if (request.getEmail() != null) {
                updateEmailById(accountId, request.getEmail());
            }
            if (request.getRoleEnum() != null) {
                updateRoleById(accountId, request.getRoleEnum());
                if (request.getRoleEnum() == RoleEnum.SELLER) {
                    existingAccount.ifPresent(account -> messageService.sendUpdateRoleSellerMessage(account.getUsername()));
                }
            }
            return new StatusResponse(true, "Account updated!");
        }
        return new StatusResponse(false, "Invalid account!");
    }

}
