package com.example.backend.model.response.cart;

import com.example.backend.model.response.OrderResponse;
import com.example.backend.model.response.product.ProductResponse;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CartDetailResponse {
    private long userId;
    private long totalPrice;
    private List<CartItemResponse> cartItems; // Chứa các sản phẩm trong giỏ hàng

    public CartDetailResponse(long userId, long totalPrice, List<CartItemResponse> cartItems) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.cartItems = cartItems;
    }
}
