//package com.example.backend.controller;
//
//import com.example.backend.model.request.frontend.cart.HandleCartRequest;
//import com.example.backend.model.response.cart.CartDetailResponse;
//import com.example.backend.model.response.cart.CartListWithFinishTimeResponse;
//import com.example.backend.model.response.StatusResponse;
//import com.example.backend.service.CartService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/cart")
//@CrossOrigin(origins = "http://localhost:4000")
//public class CartController {
//
//    @Autowired
//    private CartService cartService;
//
//    @GetMapping("/detail/{cartId}")
//    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
//    public ResponseEntity<CartDetailResponse> getCartDetail(@PathVariable(required = false) Long cartId) {
//        return ResponseEntity.ok(cartService.getCartDetail(cartId));
//    }
//
//    // USERRRR!!!
//
//    @PostMapping("/add-product/{productId}")
//    @PreAuthorize("hasAuthority('USER')")
//    public ResponseEntity<StatusResponse> addProductToCart(@PathVariable long productId, @RequestBody HandleCartRequest request) {
//        return ResponseEntity.ok(cartService.addProductToCart(productId, request));
//    }
//
//    @PutMapping("/update-product/{productId}")
//    @PreAuthorize("hasAuthority('USER')")
//    public ResponseEntity<StatusResponse> updateProductInCart(@PathVariable long productId, @RequestBody HandleCartRequest request) {
//        return ResponseEntity.ok(cartService.updateProductInCart(productId, request));
//    }
//
//    @DeleteMapping("/delete-product/{productId}")
//    @PreAuthorize("hasAuthority('USER')")
//    public ResponseEntity<StatusResponse> deleteProductInCart(@PathVariable long productId, @RequestBody HandleCartRequest request) {
//        return ResponseEntity.ok(cartService.deleteProductInCart(productId, request));
//    }
//
//    @GetMapping("/history/{userId}") // UserId dùng cho tài khoản ADMIN!!!
//    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
//    public ResponseEntity<CartListWithFinishTimeResponse> getUserCartHistory(@PathVariable(required = false) Long userId) {
//        return ResponseEntity.ok(cartService.getCartHistory(userId));
//    }
//
//
//
//
//
//}
