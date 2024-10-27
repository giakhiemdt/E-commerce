package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.entity.Seller;
import com.example.backend.model.request.frontend.ChangeAccountProfileRequest;
import com.example.backend.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private MessageService messageService;

    public boolean existSellerByAccount(Account account) {
        return sellerRepository.existsByAccount(account);
    }

    public Seller getSellerByUserName(String usserName) {
        return sellerRepository.getSellerByUserName(usserName);
    }

    @Transactional
    public void updateByAccountIdAndFullName(long accountId, String fullName) {
        sellerRepository.updateSellerByAccountIdAndFullname(fullName, accountId);
    }

    @Transactional
    public void updateByAccountIdAndPhone(long accountId, String phone) {
        sellerRepository.updateSellerByAccountIdAndPhone(phone, accountId);
    }

    @Transactional
    public void updateByAccountIdAndAddress(long accountId, String address) {
        sellerRepository.updateSellerByAccountIdAndAddress(address, accountId);
    }

    public void createSeller(Account account, ChangeAccountProfileRequest changeAccountProfileRequest) {
        sellerRepository.save(new Seller(account, changeAccountProfileRequest.getFullname(), changeAccountProfileRequest.getPhone(), changeAccountProfileRequest.getAddress()));
    }

    @Transactional
    public void updateProfile(Account account, ChangeAccountProfileRequest changeAccountProfileRequest) {
        if (!sellerRepository.existsByAccount(account)) {
            createSeller(account, changeAccountProfileRequest);
        }else {
            if (changeAccountProfileRequest.getFullname() != null) {
                updateByAccountIdAndFullName(account.getId(), changeAccountProfileRequest.getFullname());
            }
            if (changeAccountProfileRequest.getPhone() != null) {
                updateByAccountIdAndPhone(account.getId(), changeAccountProfileRequest.getPhone());
            }
            if (changeAccountProfileRequest.getAddress() != null) {
                updateByAccountIdAndAddress(account.getId(), changeAccountProfileRequest.getAddress());
            }
        }
    }

    public Seller getSellerByAccountId(long accountId) {
        return sellerRepository.getSellerByAccountId(accountId);
    }

    public boolean checkSellerHasInfo(Account account) {
        if (!existSellerByAccount(account)) {
            messageService.sendSellerNeedInfoMessage(account.getUsername());
            return false;
        }
        return true;
    }

}
