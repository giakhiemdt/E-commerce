package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.entity.JWToken;
import com.example.backend.model.response.StatusResponse;
import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.JWTokenRepository;
import com.example.backend.utility.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

    private final JWTokenRepository jwTokenRepository;
    private final JwtUtil jwtUtil;
    private final AccountRepository aRepo;

    @Autowired
    public TokenService(JWTokenRepository jwTokenRepository, JwtUtil jwtUtil, AccountRepository aRepo) {
        this.jwTokenRepository = jwTokenRepository;
        this.jwtUtil = jwtUtil;
        this.aRepo = aRepo;
    }

    @Transactional
    public void deleteToken(String token) {
        jwTokenRepository.deleteByToken(token);
    }

    @Transactional
    public void deleteByAccountId(long accountId) {
        jwTokenRepository.deleteByAccountId(accountId);
    }

    public JWToken findByToken(String token) {
        Optional<JWToken> existingJwtToken = jwTokenRepository.findByToken(token);
        return existingJwtToken.orElse(null); // Có token thì trả về không thì nullll!
    }

    @Transactional
    public boolean checkWhiteList(String token) {
        JWToken jwToken = findByToken(token);
        if (jwToken != null) { // Kiểm tra nếu tồn tại token nhe
            if (jwToken.getEndAt().before(new Date())) { // Nếu token đã hết hạn thì xóa luôn token đó và trả về false
                deleteToken(token);
                return false;
            }
            return true; // Nếu token chưa hết hạn thì trả về true đó ní;
        }
        return false; // Nếu không tồn tại token thì trả về false
    }

    @Transactional
    public StatusResponse handleLogout(Optional<Account> existingAccount) {
        return existingAccount.map(account -> {
                    deleteByAccountId(account.getId());
                    return new StatusResponse(true, "Logout successful!");
                }
                ).orElse(new StatusResponse(false, "Invalid account!"));
    }

    @Transactional
    public boolean isValidToken(String token) {
        String name = jwtUtil.extractName(token);
        if (aRepo.findByUsername(name).isPresent() && checkWhiteList(token)) {
            return jwtUtil.validateToken(token, name);
        }
        return false;
    }

}
