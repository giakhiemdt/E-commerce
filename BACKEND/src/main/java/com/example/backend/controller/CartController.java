package com.example.backend.controller;

import com.example.backend.model.request.frontend.seller.DeleteProductRequest;
import com.example.backend.model.request.frontend.user.AddCartProductRequest;
import com.example.backend.model.request.frontend.user.DeleteCartProductRequest;
import com.example.backend.model.request.frontend.user.UpdateCartProductRequest;
import com.example.backend.model.response.seller.SellerOrderResponse;
import com.example.backend.model.response.user.CartHistoryResponse;
import com.example.backend.model.response.user.UserCartResponse;
import com.example.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4000")
public class CartController {

    @Autowired
    private CartService cartService;

    // USERRRR!!!

    @GetMapping("/user/cart")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserCartResponse> getUserCart() {
        return ResponseEntity.ok(cartService.getUserCart());
    }

    @PostMapping("/user/add-product")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Boolean> addProductToCart(@RequestBody AddCartProductRequest addCartProductRequest) {
        cartService.addProductToCart(addCartProductRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/update-product")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Boolean> updateProductInCart(@RequestBody UpdateCartProductRequest updateCartProductRequest) {
        cartService.updateProductInCart(updateCartProductRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/delete-product")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Boolean> deleteProductInCart(@RequestBody DeleteCartProductRequest deleteCartProductRequest) {
        cartService.deleteProductInCart(deleteCartProductRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/cart-history")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<CartHistoryResponse>> getUserCartHistory() {
        return ResponseEntity.ok(cartService.getCartHistory());
    }

    //SELLEEEEEEEEEERRRRRRRRRRRRRRRR

    @GetMapping("/seller/orders")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<List<SellerOrderResponse>> getSellerOrder() {
        return ResponseEntity.ok();
    }

}
