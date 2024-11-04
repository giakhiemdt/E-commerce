package com.example.backend.model.request.frontend.order;

import com.example.backend.entity.enums.PaymentMethodEnum;
import lombok.Data;

import java.util.List;

@Data
public class OrderListRequest {
    private PaymentMethodEnum paymentType; // Online và COD
    private List<Long> ordersId;
}
