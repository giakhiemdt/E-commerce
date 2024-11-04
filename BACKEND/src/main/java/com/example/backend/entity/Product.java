package com.example.backend.entity;


import com.example.backend.entity.enums.OrderStatusEnum;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @ManyToOne
    @JoinColumn(
            name = "seller_id",
            nullable = false
    )
    @JsonBackReference
    private Seller seller;
    @ManyToOne
    @JoinColumn(
            name = "product_type_id",
            nullable = false
    )
    @JsonBackReference
    private ProductType productType;
    private String name;
    private int quantity;
    private boolean isActive;
    @OneToOne(
            mappedBy = "product",
            cascade = {CascadeType.ALL}
    )
    @JsonIgnore
    @JsonManagedReference
    private ProductDetail productDetail;

    @OneToMany(
            mappedBy = "product",
            cascade = {CascadeType.ALL}
    )
    @JsonManagedReference
    @JsonIgnore
    private List<Orders> orders;
    @OneToMany(
            mappedBy = "product",
            cascade = {CascadeType.ALL}
    )
    @JsonIgnore
    @JsonManagedReference
    private List<FeedBack> feedBacks;

    public  Product(Seller seller, ProductType productType, String name, int quantity, boolean isActive) {
        this.seller = seller;
        this.productType = productType;
        this.name = name;
        this.quantity = quantity;
        this.isActive = isActive;
    }

    public Product() {

    }


}
