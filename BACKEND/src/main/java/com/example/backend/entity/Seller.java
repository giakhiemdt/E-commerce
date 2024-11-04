package com.example.backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;

import lombok.Data;


@Entity
@Data
public class Seller {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @OneToOne
    @JoinColumn(
            name = "account_id",
            nullable = false
    )
    @JsonBackReference
    private Account account;
    @Column(
            nullable = false
    )
    private String fullname;
    @Column(
            nullable = false
    )
    private String phone;
    @Column(
            nullable = false
    )
    private String address;
    @OneToMany(
            mappedBy = "seller",
            cascade = {CascadeType.ALL}
    )
    @JsonIgnore
    @JsonManagedReference
    private List<Product> products;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private List<Payment> payments;

    public Seller(Account account, String fullname, String phone, String address) {
        this.account = account;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
    }

    public Seller() {

    }
}
