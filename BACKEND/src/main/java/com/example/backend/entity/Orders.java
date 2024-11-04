package com.example.backend.entity;

import com.example.backend.entity.enums.OrderStatusEnum;
import com.example.backend.entity.enums.PaymentMethodEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
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
    @Column
    private PaymentMethodEnum paymentMethod;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrdersStatus> ordersStatusList;

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Shipment shipment;

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Payment payment;

    public Orders() {}

    public Orders(Users users, Product product, int quantity, OrderStatusEnum status) {
        this.users = users;
        this.quantity = quantity;
        this.product = product;
        this.status = status;
    }

    public Timestamp getOrderedDate() {
        for (OrdersStatus ordersStatus : ordersStatusList) {
            if (ordersStatus.getStatus().equals(OrderStatusEnum.ORDERED)) {
                return ordersStatus.getCreatedDate();
            }
        }
        return null;
    }

    public long getNeedPaid() {
        for (OrdersStatus ordersStatus : ordersStatusList) {
            if (ordersStatus.getStatus().equals(OrderStatusEnum.ORDERED)) {
                return ordersStatus.getNeedPaid();
            }
        }
        return 0;
    }
}
