package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class TokenBlackList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Timestamp createdAt;

    public TokenBlackList(String token) {
        this.token = token;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
