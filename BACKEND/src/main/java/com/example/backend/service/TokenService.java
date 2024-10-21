package com.example.backend.service;

import com.example.backend.entity.TokenBlackList;
import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.TokenBlackListRepository;
import com.example.backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenBlackListRepository tblRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccountRepository aRepo;

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

    public boolean isValidToken(String authHeader) {
        if (authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String name = jwtUtil.extractName(token);
            if (aRepo.findByUsername(name).isPresent()) {
                return jwtUtil.validateToken(token, name);
            }
        }
        return false;
    }
}
