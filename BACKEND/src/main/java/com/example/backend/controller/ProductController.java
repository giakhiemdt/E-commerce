package com.example.backend.controller;

import com.example.backend.entity.Account;
import com.example.backend.entity.Product;
import com.example.backend.entity.ProductType;
import com.example.backend.entity.Seller;
import com.example.backend.model.request.frontend.AddProductRequest;
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
public class ProductController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SellerService sellerService;

    @GetMapping("/product-types") // Cái này thì thằng nào lấy cũng được!
    public ResponseEntity<List<ProductType>> getProductTypes() {
        return ResponseEntity.ok(productService.getAllProductType());
    }

    @GetMapping("/productid") // Này là xem thông tin chi tiết sản phẩm nhưng mà t chưa viết frontend
    public ResponseEntity<Optional<Product>> getProductById(@RequestParam long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/productname") // T dự định để tìm kiếm sản phẩm nhưng mà chưa viết frontend
    public ResponseEntity<List<Product>> getProductByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @GetMapping("/products") // Này là lấy danh sách sản phẩm, cái này SELLER và ADMIN dùng được
    public ResponseEntity<List<Product>> getAllProducts(@RequestHeader("AuthenticationToken") String token) {
        if (tokenService.isValidToken(tokenService.trueToken(token))) {
            Optional<Account> existingAccount = accountService.findByUserName(jwtUtil.extractName(tokenService.trueToken(token)));
            if (existingAccount.isPresent()) {
                if (tokenService.isADMIN(tokenService.trueToken(token))) { // Nếu là admin thì lấy danh sách tất cả sản phẩm
                    return ResponseEntity.ok(productService.getAllProduct());
                }else if (tokenService.isSELLER(tokenService.trueToken(token))) { // Nếu là người bán thì lấy danh sách sản phẩm đăng bán của người đó
                    if (sellerService.checkSellerHasInfo(existingAccount.get())) { // cái này phải đặt trong tất cả hành động của Seller
                        Seller seller = sellerService.getSellerByAccountId(existingAccount.get().getId());
                        return ResponseEntity.ok(productService.findProductBySellerId(seller.getId()));
                    }
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

//    @PostMapping("/add-product")
//    public ResponseEntity<Boolean> addProduct(@RequestHeader("AuthenticationToken") String token, @RequestBody AddProductRequest addProductRequest) {
//
//    }

}
