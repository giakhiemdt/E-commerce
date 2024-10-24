package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.entity.Product;
import com.example.backend.entity.Seller;
import com.example.backend.model.request.frontend.AccProfileRequest;
import com.example.backend.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private MessageService messageService;

    public boolean existSellerByAccount(Account account) {
        return sellerRepository.existsByAccount(account);
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

    public void createSeller(Account account, AccProfileRequest accProfileRequest) {
        sellerRepository.save(new Seller(account, accProfileRequest.getFullname(), accProfileRequest.getPhone(), accProfileRequest.getAddress()));
    }

    @Transactional
    public void updateProfile(Account account, AccProfileRequest accProfileRequest) {
        if (!sellerRepository.existsByAccount(account)) {
            createSeller(account, accProfileRequest);
        }else {
            if (accProfileRequest.getFullname() != null) {
                updateByAccountIdAndFullName(account.getId(), accProfileRequest.getFullname());
            }
            if (accProfileRequest.getPhone() != null) {
                updateByAccountIdAndPhone(account.getId(), accProfileRequest.getPhone());
            }
            if (accProfileRequest.getAddress() != null) {
                updateByAccountIdAndAddress(account.getId(), accProfileRequest.getAddress());
            }
        }
    }

    public Seller getSellerByAccountId(long accountId) {
        return sellerRepository.getSellerByAccountId(accountId);
    }

    public boolean checkSellerHasInfo(Account account) {
        if (!existSellerByAccount(account)) {
            System.out.println("Nihaomamamma");
            messageService.sendSellerNeedInfoMessage(account.getUsername());
            return false;
        }
        return true;
    }

}
