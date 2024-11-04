package com.example.backend.controller;

import com.example.backend.entity.enums.OrderStatusEnum;
import com.example.backend.model.request.frontend.order.HandleOrderRequest;
import com.example.backend.model.request.frontend.order.OrderListRequest;
import com.example.backend.model.response.StatusResponse;
import com.example.backend.model.response.cart.CartDetailResponse;
import com.example.backend.model.response.order.SellerOrderResponse;
import com.example.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "http://localhost:4000")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Lấy những product có trong giỏ hàng.
    @GetMapping("/cart-detail/{userId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<CartDetailResponse> getCartDetail(@PathVariable(required = false) Long userId) {
        return ResponseEntity.ok(orderService.getCurrentCart(userId));
    }

    // USERRRR!!!

    @PostMapping("/add-product/{productId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<StatusResponse> addProductToCart(@PathVariable long productId, @RequestBody HandleOrderRequest request) {
        return ResponseEntity.ok(orderService.addProductToCart(productId, request));
    }

    @PutMapping("/update-product/{productId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<StatusResponse> updateProductInCart(@PathVariable long productId, @RequestBody HandleOrderRequest request) {
        return ResponseEntity.ok(orderService.updateProductInCart(productId, request));
    }

    @DeleteMapping("/delete-product/{productId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<StatusResponse> deleteProductInCart(@PathVariable long productId, @RequestBody HandleOrderRequest request) {
        return ResponseEntity.ok(orderService.deleteProductFromCart(productId, request));
    }

    @PostMapping("/pay-orders") // Gửi danh sách sản phẩm đã order về xem như đã thanh toán.
    @PreAuthorize("hasAuthority('USER')") // Chỉ user mới thanh toán được tiền đơn hàng.
    public ResponseEntity<StatusResponse> payOnline(@RequestBody OrderListRequest request) {
        return ResponseEntity.ok(orderService.orderProducts(request));
    }

    //SELLEEEEEEEEEERRRRRRRRRRRRRRRR

    @GetMapping("/order-waiting") // Đơn hàng đang chờ xác nhận
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<List<SellerOrderResponse>> getSellerOrder() {
        return ResponseEntity.ok(orderService.getCurrentOrder());
    }

    @GetMapping("/order-history") // Lịch sử đơn hàng
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<List<SellerOrderResponse>> getSellerOrderHistory() {
        return ResponseEntity.ok(orderService.getOrderHistory());
    }

    @PutMapping("/accept-order/{orderId}") // Chấp nhận đơn hàng
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<StatusResponse> handleAcceptOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.handleOrderStatus(orderId, OrderStatusEnum.ACCEPTED));
    }

    @PutMapping("/prepared-order/{orderId}") // Đã chuẩn bị xong đơn hàng
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<StatusResponse> handleReparedOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.handleOrderStatus(orderId, OrderStatusEnum.PREPARED));
    }

    @PutMapping("/shipping-order/{orderId}")
    @PreAuthorize("hasAuthority('SELLER')") // Nếu rảnh thì mở rộng thêm 1 role mới là DeliveryMan chịu tránh nhiệm giao hàng
    public ResponseEntity<StatusResponse> handleShippingOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.handleOrderStatus(orderId, OrderStatusEnum.SHIPPING));
    }

    @PutMapping("/finished/{orderId}")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<StatusResponse> handleFinishedOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.finishOrder(orderId));
    }



}
