package com.example.backend.entity;

import com.example.backend.entity.enums.ShipmentStatusEnum;
import jakarta.persistence.*;
import com.example.backend.entity.ShipmentStatus;

import java.sql.Timestamp;

@Entity
public class ShipmentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;

    private Timestamp createdDate;

    private ShipmentStatusEnum status;

    private String note;
}
