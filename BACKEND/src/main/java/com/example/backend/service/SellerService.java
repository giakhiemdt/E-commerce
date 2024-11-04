package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.entity.Seller;
import com.example.backend.model.request.frontend.account.UpdateAccountProfileRequest;
import com.example.backend.model.response.account.AccountProfileResponse;
import com.example.backend.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public boolean existSellerByAccount(Account account) {
        return account.getSeller() != null;
    }

    public Seller getSellerByUserName(String usserName) {
        return sellerRepository.getSellerByUserName(usserName);
    }

    @Transactional
    public void updateSellerFullName(String fullName, Seller seller) {
        sellerRepository.updateSellerFullNameBySeller(fullName, seller);
    }

    @Transactional
    public void updateSellerPhone(String phone, Seller seller) {
        sellerRepository.updateSellerPhoneBySeller(phone, seller);
    }

    @Transactional
    public void updateSellerAddress(String address, Seller seller) {
        sellerRepository.updateSellerAddressBySeller(address, seller);
    }

    public void createSeller(Account account, UpdateAccountProfileRequest request) {
        sellerRepository.save(new Seller(account, request.getFullName(), request.getPhone(), request.getAddress()));
    }

    public AccountProfileResponse getSellerProfile(Account account) {
        return new AccountProfileResponse(
                account.getSeller().getFullname(),
                account.getSeller().getPhone(),
                account.getSeller().getAddress()
        );
    }

    @Transactional
    public void updateSellerProfile(UpdateAccountProfileRequest request, Account account) {
        if (!existSellerByAccount(account)) {
            createSeller(account, request);
        }
        Seller seller = account.getSeller();
        if (request.getFullName() != null) {
            updateSellerFullName(request.getFullName(), seller);
        }
        if (request.getPhone() != null) {
            updateSellerPhone(request.getPhone(), seller);
        }
        if (request.getAddress() != null) {
            updateSellerAddress(request.getAddress(), seller);
        }
    }

}
