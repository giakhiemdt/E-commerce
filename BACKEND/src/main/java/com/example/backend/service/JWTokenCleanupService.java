package com.example.backend.service;

import com.example.backend.repository.JWTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JWTokenCleanupService {

    private final JWTokenRepository jwtTokenRepository;

    public JWTokenCleanupService(JWTokenRepository jwtTokenRepository) {
        this.jwtTokenRepository = jwtTokenRepository;
    }

    // Đặt lịch chạy mỗi 1 phút sẽ tự động kiểm tra token hết hạn nè!
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cleanExpiredTokens() {
        jwtTokenRepository.deleteExpiredTokens();
    }
}

