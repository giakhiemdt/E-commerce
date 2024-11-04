package com.example.backend.entity;

import com.example.backend.entity.enums.OrderStatusEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class OrdersStatus {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    private long needPaid;
    private Timestamp createdDate;
    private OrderStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    @OneToOne(mappedBy = "ordersStatus", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Transactions transactions;

    public OrdersStatus(long needPaid, OrderStatusEnum status, Orders orders) {
        this.needPaid = needPaid;
        this.status = status;
        this.orders = orders;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public OrdersStatus() {

    }
}
