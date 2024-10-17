package com.example.backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Generated;

@Entity
@Data
public class CartDetail {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @OneToOne
    @JoinColumn(
            name = "cart",
            nullable = false
    )
    @JsonBackReference
    private Cart cart;
    private String phone;
    private String address;
}
