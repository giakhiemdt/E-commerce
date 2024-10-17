package com.example.backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.Generated;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    @JsonBackReference
    private User user;
    @Column(
            nullable = false
    )
    private Timestamp createdDate = new Timestamp(System.currentTimeMillis());
    @Column(
            nullable = false
    )
    private Status status;
    @OneToOne(
            mappedBy = "cart",
            cascade = {CascadeType.ALL}
    )
    @JsonIgnore
    @JsonManagedReference
    private CartDetail cartDetail;
    @OneToMany(
            mappedBy = "cart",
            cascade = {CascadeType.ALL}
    )
    @JsonManagedReference
    @JsonIgnore
    private List<Add> adds;
    @OneToOne(
            mappedBy = "cart",
            cascade = {CascadeType.ALL}
    )
    @JsonIgnore
    @JsonManagedReference
    private Payment payment;
    @OneToMany(
            mappedBy = "cart",
            cascade = {CascadeType.ALL}
    )
    @JsonIgnore
    @JsonManagedReference
    private List<CartStatus> cartStatuses;
}
