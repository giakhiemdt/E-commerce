package com.example.backend.controller;

import com.example.backend.entity.Product;
import com.example.backend.model.request.frontend.AddProductRequest;
import com.example.backend.model.response.ProductDetailResponse;
import com.example.backend.model.response.ProductTypesResponse;
import com.example.backend.model.response.ProductResponse;
import com.example.backend.service.AccountService;
import com.example.backend.service.ProductService;
import com.example.backend.service.SellerService;
import com.example.backend.service.TokenService;
import com.example.backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    //PUBLIC API ~~~Yay!!!

    // Này là lấy danh sách sản phẩm, cái này ai cũng lấy được
    // Lưu ý là ADMIN và SELLER sẽ không sử dụng đường dẫn này!!!
    @GetMapping("/products")
    public ResponseEntity<Map<String, List<ProductResponse>>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProductSplitWithTypeId());
    }

    @GetMapping("/product-types") // Cái này thì thằng nào lấy cũng được!
    public ResponseEntity<List<ProductTypesResponse>> getProductTypes() {
        return ResponseEntity.ok(productService.getProductTypes());
    }

   // Này là xem thông tin chi tiết sản phẩm nhưng mà t chưa viết frontend
   // Giờ viết nè!!
   @GetMapping("/product-detail")
   public ResponseEntity<ProductDetailResponse> getProductById(@RequestParam long id) {
        return ResponseEntity.ok(productService.getProductDetail(id));
    }

    //Thằng này dùng để kiếm product theo keyword, t cũng không biết nó chạy ổn không nữa ~~
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }

    // SELLER API Noooo!!!

    @GetMapping("/my-products") // Lấy danh sách sản phẩm của SELLER. Cái này chỉ có Seller dùng được!!!
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<List<Product>> getMyProducts() {
        return ResponseEntity.ok(productService.getMyProducts());
    }




    @PostMapping("/add-product") //Theo t chỉ có SELLER mới có thể tạo Product thôi
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<Boolean> addProduct(@RequestBody AddProductRequest addProductRequest) {
        productService.createProduct(addProductRequest);
        return ResponseEntity.ok().build();
    }

}
