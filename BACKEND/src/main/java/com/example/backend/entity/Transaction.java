package com.example.backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.sql.Timestamp;

import lombok.Data;
import lombok.Generated;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "payment_id",
            nullable = false
    )
    @JsonBackReference
    private Payment payment;
    @OneToOne
    @JoinColumn(
            name = "cart_status_id",
            nullable = false
    )
    @JsonBackReference
    private CartStatus cartStatus;
    private long fee;
    private Timestamp createdDate;
    private String notes;
}