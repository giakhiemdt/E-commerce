package com.example.backend.model.response.user;

import com.example.backend.entity.Status;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CartHistoryResponse {
    private long id;
    private Timestamp createdDate;
    private Status status;
    private String phone;
    private String address;

    public CartHistoryResponse(long id, Timestamp createdDate, Status status, String phone, String address) {
        this.id = id;
        this.createdDate = createdDate;
        this.status = status;
        this.phone = phone;
        this.address = address;
    }
}
