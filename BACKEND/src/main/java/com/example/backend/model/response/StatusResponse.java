package com.example.backend.model.response;

import lombok.Data;

@Data
public class StatusResponse {
    private boolean success;
    private String message;
    public StatusResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
