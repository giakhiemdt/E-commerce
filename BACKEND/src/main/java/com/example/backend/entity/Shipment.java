package com.example.backend.entity;

import com.example.backend.entity.enums.ShipmentStatusEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    private ShipmentStatusEnum status;

    private String note;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ShipmentStatus> shipmentStatusList;

}
