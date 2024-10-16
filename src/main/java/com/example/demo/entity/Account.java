package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Timestamp createdDate;

    public Account() {
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Account(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdDate = new Timestamp(System.currentTimeMillis()); // Cập nhật thời gian tạo
    }
}
