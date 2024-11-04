package com.example.backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.sql.Timestamp;

import lombok.Data;
import lombok.Generated;

@Entity
@Data
public class ProductDetail {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @OneToOne
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    @JsonBackReference
    private Product product;
    private long productPrice;
    private long systemFee;
    private int discount;

    @Column(
            nullable = false
    )
    private Timestamp postedDate;
    @Column(
            nullable = false
    )
    private int sale;
    private String description;
    private String imageUrl;

    public ProductDetail(Product product, long productPrice, long systemFee, int discount, String description, String imageUrl) {
        this.product = product;
        this.productPrice = productPrice;
        this.systemFee = systemFee;
        this.discount = discount;
        this.description = description;
        this.imageUrl = imageUrl;
        this.postedDate = new Timestamp(System.currentTimeMillis());
    }

    public ProductDetail() {

    }
}
