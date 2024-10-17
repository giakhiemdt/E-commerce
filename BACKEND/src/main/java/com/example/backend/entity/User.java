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
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @OneToOne
    @JoinColumn(
            name = "account_id",
            nullable = false
    )
    @JsonBackReference
    private Account account;
    private String fullname;
    private String phone;
    private String address;
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.ALL}
    )
    @JsonIgnore
    @JsonManagedReference
    private List<Cart> carts;
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.ALL}
    )
    @JsonIgnore
    @JsonManagedReference
    private List<FeedBack> feedBacks;
}
