package com.example.backend.repository;

import com.example.backend.entity.JWToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface JWTokenRepository extends JpaRepository<JWToken, Long> {

    public Optional<JWToken> findByToken(String token);

    public List<JWToken> findAllByAccountId(Long accountId);

    public boolean existsByToken(String token);

    @Modifying
    @Transactional
    public void deleteByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM JWToken t WHERE t.endAt < CURRENT_TIMESTAMP")
    void deleteExpiredTokens();

}
