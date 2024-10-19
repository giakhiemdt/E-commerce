package com.example.backend.service;

import com.example.backend.entity.TokenBlackList;
import com.example.backend.repository.TokenBlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    TokenBlackListRepository tblRepo;

    public void addBlackList(String token) {
        tblRepo.save(new TokenBlackList(token));
    }

    public boolean checkBlackList(String token) {
        return tblRepo.existsByToken(token);
    }

    public String handleLogout(String authHeader) {
        if (authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (!checkBlackList(token)) {
                addBlackList(token);
                return "Successful";
            }
        }
        return "Failed";
    }
}
