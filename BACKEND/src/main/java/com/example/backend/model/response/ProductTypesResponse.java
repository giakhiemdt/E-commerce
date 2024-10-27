package com.example.backend.model.response;

import lombok.Data;

@Data
public class ProductTypesResponse {
    private long id;
    private String name;

    public ProductTypesResponse(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
