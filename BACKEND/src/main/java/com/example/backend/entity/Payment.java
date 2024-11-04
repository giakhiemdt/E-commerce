package com.example.backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Entity
@Data
public class  Payment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne
    @JoinColumn( name = "seller_id", nullable = false)
    @JsonBackReference
    private Seller seller;

    @OneToOne
    @JoinColumn(
            name = "orders_id",
            nullable = false
    )
    @JsonBackReference
    private Orders orders;

    private long totalFee;
    private Timestamp createdDate;
    @OneToMany(
            mappedBy = "payment",
            cascade = {CascadeType.ALL}
    )
    @JsonManagedReference
    @JsonIgnore
    private List<Transactions> transactions;

    public Payment(Seller seller, Orders orders, long totalFee) {
        this.seller = seller;
        this.orders = orders;
        this.totalFee = totalFee;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Payment() {

    }
}
