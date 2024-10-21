package com.example.backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Timestamp;

import lombok.Data;

@Entity
@Data
public class FeedBack {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @ManyToOne
    @JoinColumn(
            name = "users_id",
            nullable = false
    )
    @JsonBackReference
    private Users users;
    @ManyToOne
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    @JsonBackReference
    private Product product;
    private String comment;
    private float rating;
    private Timestamp createdDate;
}
