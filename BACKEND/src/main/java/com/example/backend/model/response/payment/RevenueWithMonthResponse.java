package com.example.backend.model.response.payment;

import lombok.Data;

@Data
public class RevenueWithMonthResponse {

    int totalOrder; // Số lượng đơn hàng trong tháng
    long grossRevenue; // Tổng danh thu từ tất cả các giao dịch bao gồm 5% hoa hồng cho sàn
    long grossMerchandiseValue; // Tổng giá trị sản phẩm bán ra, không tính 5% hoa hồng cho sàn
    long operationCost; // Chi phí hoạt động của sàn
    long netProfit; // Lợi nhuận ròng sau khi trừ ra chi phí hoạt động của sàn
    int commissionRate; // Tỷ lệ hoa hồng
    long commissionEarned; // Lợi nhuận thu được từ hoa hồng

    public RevenueWithMonthResponse(){}

    public RevenueWithMonthResponse(int totalOrder, long grossRevenue,
                                    long grossMerchandiseValue, long operationCost) {
        this.totalOrder = totalOrder;
        this.grossRevenue = grossRevenue;
        this.grossMerchandiseValue = grossMerchandiseValue;
        this.operationCost = operationCost;
        this.netProfit = grossRevenue - operationCost;
        this.commissionRate = 5;
        this.commissionEarned = grossRevenue - grossMerchandiseValue;
    }

    public RevenueWithMonthResponse(int totalOrder, long grossRevenue, long grossMerchandiseValue) {
        this.totalOrder = totalOrder;
        this.grossRevenue = grossRevenue;
        this.grossMerchandiseValue = grossMerchandiseValue;
    }
}

