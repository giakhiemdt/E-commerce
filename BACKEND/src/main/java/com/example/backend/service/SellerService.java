package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public boolean existSellerByAccount(Account account) {
        return sellerRepository.existsByAccount(account);
    }

}
