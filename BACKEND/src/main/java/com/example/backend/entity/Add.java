package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Generated;

@Entity
@Data
public class Add {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "cart_id",
            nullable = false
    )
    @JsonBackReference
    private Cart cart;
    @ManyToOne
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    @JsonBackReference
    private Product product;
    @Column(
            nullable = false
    )
    private int quantity;
    private OrderStatus orderStatus;

    public Add() {}

    public Add(Cart cart, Product product, int quantity, OrderStatus orderStatus) {
        this.cart = cart;
        this.quantity = quantity;
        this.product = product;
        this.orderStatus = orderStatus;
    }
}
