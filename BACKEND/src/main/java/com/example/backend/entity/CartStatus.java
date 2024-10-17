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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.sql.Timestamp;

import lombok.Data;
import lombok.Generated;

@Entity
@Data
public class CartStatus {
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
    private String status;
    private Timestamp createdDate;
    private String notes;
    @OneToOne(
            mappedBy = "cartStatus",
            cascade = {CascadeType.ALL}
    )
    @JsonManagedReference
    @JsonIgnore
    private Transaction transaction;
}
