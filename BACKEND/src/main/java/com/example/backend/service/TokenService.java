package com.example.backend.service;

import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.JWTokenRepository;
import com.example.backend.utility.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private JWTokenRepository jwTokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccountRepository aRepo;

//    public void addBlackList(String token) {
//        tblRepo.save(new TokenBlackList(token));
//    }

    @Transactional
    public void deleteToken(String token) {
        jwTokenRepository.deleteByToken(token);
    }

    public boolean checkWhiteList(String token) {
        return jwTokenRepository.existsByToken(token);
    }

    @Transactional
    public String handleLogout(String authHeader) {
        if (authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (checkWhiteList(token)) {
                deleteToken(token);
                return "Successful";
            }
        }
        return "Failed";
    }

    public boolean isValidToken(String authHeader) {
        if (authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String name = jwtUtil.extractName(token);
            if (aRepo.findByUsername(name).isPresent() && jwTokenRepository.existsByToken(token)) { 
                return jwtUtil.validateToken(token, name);
            }

        }
        return false;
    }
}
