package com.example.backend.utility;

import com.example.backend.entity.Account;
import com.example.backend.entity.JWToken;
import com.example.backend.entity.Role;
import com.example.backend.repository.JWTokenRepository;
import com.example.backend.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Autowired
    JWTokenRepository jwTokenRepository;

//    private final Key SECRET_KEY =  new KeyUtil().getSecretKey();
    private final Key SECRET_KEY =  KeyUtil.getSecretKey();

    public String generateToken(Account account, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role.toString());
        Date createdDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(createdDate.getTime() + 1000 * 60 * 60 * 24);
        String token = createToken(claims, account.getUsername(), createdDate, expirationDate);
        jwTokenRepository.save(new JWToken(token, account, createdDate, expirationDate));
        return token;
    }

    private String createToken(Map<String, Object> claims, String name, Date createdDate, Date expirationDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(name)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate) // 1 hour
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean validateToken(String token, String name) { // Lỏ không cần!!!
        final String extractedName = extractName(token);
        return name.equals(extractedName) && !isTokenExpired(token);
    }

    public String extractName(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        Claims claims = extractClaims(token);
        return claims.get("role", String.class);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }
}
