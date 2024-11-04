package com.example.backend.entity;


import com.example.backend.entity.enums.OrderStatusEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

import lombok.Data;

@Entity
@Data
public class Users {
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
            mappedBy = "users",
            cascade = {CascadeType.ALL}
    )
    @JsonIgnore
    @JsonManagedReference
    private List<Orders> orders;
    @OneToMany(
            mappedBy = "users",
            cascade = {CascadeType.ALL}
    )
    @JsonIgnore
    @JsonManagedReference
    private List<FeedBack> feedBacks;

    public Users(Account account) {
        this.account = account;
    }

    public Users() {

    }

    public long getTotalPriceInCurrentCart() {

        return orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatusEnum.CREATED))
                .mapToLong(order -> order.getQuantity() *
                        (order.getProduct().getProductDetail().getProductPrice()
                        +order.getProduct().getProductDetail().getSystemFee()))
                .sum();

    }
    
}
