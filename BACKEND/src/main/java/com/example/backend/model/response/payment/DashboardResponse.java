package com.example.backend.model.response.payment;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DashboardResponse {

    int year;
    int totalOrders; // Số lượng đơn hàng được đặt trong 1 năm
    Map<Integer, RevenueWithMonthResponse> monthRevenues; // Map chứa tổng quan doanh thu của một năm

    public DashboardResponse() {
    }

    public DashboardResponse(int year, int totalOrders, Map<Integer, RevenueWithMonthResponse> monthRevenues) {
        this.year = year;
        this.totalOrders = totalOrders;
        this.monthRevenues = monthRevenues;
    }
}
