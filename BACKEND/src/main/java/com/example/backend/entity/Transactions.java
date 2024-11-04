package com.example.backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Timestamp;

import lombok.Data;

@Entity
@Data
@Table(name = "transactions")
public class Transactions {
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
            name = "orders_status_id",
            nullable = false
    )
    @JsonBackReference
    private OrdersStatus ordersStatus;

    private long fee;
    private Timestamp createdDate;
    private String notes;

    public Transactions(Payment payment, OrdersStatus ordersStatus, long fee) {
        this.payment = payment;
        this.ordersStatus = ordersStatus;
        this.fee = fee;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }
    public Transactions() {}
}