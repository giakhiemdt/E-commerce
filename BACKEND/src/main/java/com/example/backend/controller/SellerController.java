package com.example.backend.controller;

import com.example.backend.entity.Account;
import com.example.backend.entity.Product;
import com.example.backend.entity.Seller;
import com.example.backend.model.request.frontend.AccProfileRequest;
import com.example.backend.service.AccountService;
import com.example.backend.service.ProductService;
import com.example.backend.service.SellerService;
import com.example.backend.service.TokenService;
import com.example.backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4000")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/update-profile")
    public ResponseEntity<Boolean> updateProfile(@RequestHeader("AuthenticationToken") String token, @RequestBody() AccProfileRequest accProfileRequest) {
        System.out.println("DUmajama");

        if (tokenService.isValidToken(tokenService.trueToken(token))) {
            Optional<Account> existingAccount = accountService.findByUserName(jwtUtil.extractName(tokenService.trueToken(token)));
            if (existingAccount.isPresent()) {
                sellerService.updateProfile(existingAccount.get(), accProfileRequest);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();

    }

    @GetMapping(value = "/my-product")
    public ResponseEntity<List<Product>> getMyProduct(@RequestHeader("AuthenticationToken") String token) {
        if (tokenService.isValidToken(tokenService.trueToken(token))) {
            Optional<Account> existingAccount = accountService.findByUserName(jwtUtil.extractName(tokenService.trueToken(token)));
            if (existingAccount.isPresent()) {
                if (sellerService.checkSellerHasInfo(existingAccount.get())) { // cái này phải đặt trong tất cả hành động của Seller
                    Seller seller = sellerService.getSellerByAccountId(existingAccount.get().getId());
                    return ResponseEntity.ok(productService.findProductBySellerId(seller.getId()));
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

}
