package com.example.backend.entity.enums;

public enum OrderStatusEnum {
    CREATED, // Vừa thêm sản phẩm vào
    ORDERED, // Đặt sản phẩm
    ACCEPTED, // Seller chấp nhận đơn hàng
    PREPARED, // Seller đã chuẩn bị đơn hàng
    SHIPPING, // Đơn hàng đang được giao
    REJECTED, // Đơn hàng bị từ chối
    CANCELLED, // Đơn hàng bị hủy
    FINISHED // Đơn hàng hoàn thành
}
