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

    Optional<JWToken> findByToken(String token);

    List<JWToken> findAllByAccountId(Long accountId);

    boolean existsByToken(String token);

    @Modifying
    @Transactional
    void deleteByToken(String token);

    @Modifying
    @Transactional
    void deleteByAccountId(Long accountId);

    @Modifying
    @Transactional
    @Query("DELETE FROM JWToken t WHERE t.endAt < CURRENT_TIMESTAMP")
    void deleteExpiredTokens();

}
