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

    // Đặt lịch chạy hàng ngày (ví dụ mỗi ngày lúc 12 giờ đêm)
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void cleanExpiredTokens() {
        jwtTokenRepository.deleteExpiredTokens();
    }
}

