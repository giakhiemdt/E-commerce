package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

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

    @Column(nullable = false)
    private boolean isActive;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Users users;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Seller seller;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<JWToken> jwTokens;

    public Account() {
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Account(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdDate = new Timestamp(System.currentTimeMillis()); // Cập nhật thời gian tạo
        this.isActive = true;
    }
}
