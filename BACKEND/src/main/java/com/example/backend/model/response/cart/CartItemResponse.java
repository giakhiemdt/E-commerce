package com.example.backend.model.response.cart;

import com.example.backend.model.response.OrderResponse;
import com.example.backend.model.response.product.ProductResponse;
import lombok.Data;

@Data
public class CartItemResponse {
    private ProductResponse product; // Thông tin sản phẩm
    private OrderResponse order;            // Số lượng sản phẩm trong giỏ hàng

    public CartItemResponse(ProductResponse product, OrderResponse order) {
        this.product = product;
        this.order = order;
    }
}
