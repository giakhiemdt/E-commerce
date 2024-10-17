package com.example.backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;

import lombok.Data;
import lombok.Generated;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @OneToOne
    @JoinColumn(
            name = "cart_id",
            nullable = false
    )
    @JsonBackReference
    private Cart cart;
    private long totalFee;
    @OneToMany(
            mappedBy = "payment",
            cascade = {CascadeType.ALL}
    )
    @JsonManagedReference
    @JsonIgnore
    private List<Transaction> transactions;
}
