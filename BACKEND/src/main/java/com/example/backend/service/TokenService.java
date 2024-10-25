package com.example.backend.service;

import com.example.backend.entity.JWToken;
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

    public boolean existToken(String token) { // Kiểm tra token có tồn tại hay không, cái này không kiểm tra thời gian hết hạn đâu
        return jwTokenRepository.existsByToken(token);
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
    public boolean handleLogout(String authHeader) {
        String token = trueToken(authHeader);
        if (checkWhiteList(token)) {
            deleteToken(token);
            return true;
        }
        return false;
    }

    public boolean isValidToken(String token) {
        String name = jwtUtil.extractName(token);
        if (aRepo.findByUsername(name).isPresent() && jwTokenRepository.existsByToken(token)) {
            return jwtUtil.validateToken(token, name);
        }
        return false;
    }

    public boolean isADMIN(String token) {
        String rolw = jwtUtil.extractRole(token);
        return rolw.equals("ADMIN");
    }

    public boolean isSELLER(String token) {
        String rolw = jwtUtil.extractRole(token);
        return rolw.equals("SELLER");
    }

    public boolean isUSER(String token) {
        String rolw = jwtUtil.extractRole(token);
        return rolw.equals("USER");
    }

    public String trueToken(String authHeader) {
        if (authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return token;
        }
        return null;
    }


}
