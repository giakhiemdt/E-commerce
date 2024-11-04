package com.example.backend.controller;

import com.example.backend.model.request.frontend.product.HandleProductRequest;
import com.example.backend.model.response.StatusResponse;
import com.example.backend.model.response.product.*;
import com.example.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4000")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController (ProductService productService) {
        this.productService = productService;
    }

    //PUBLIC API ~~~Yay!!!

    // Này là lấy danh sách sản phẩm, cái này ai cũng lấy được
    // ADMIN và SELLER cũng sẽ sử dụng đường dẫn này!!!
    @GetMapping("/products")
    public ResponseEntity<ProductListWithTypeResponse> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProductSplitWithTypeId());
    }

    @GetMapping("/product-types") // Cái này thì thằng nào lấy cũng được!
    public ResponseEntity<ProductTypeListResponse> getProductTypes() {
        return ResponseEntity.ok(productService.getProductTypes());
    }

   // Này là xem thông tin chi tiết sản phẩm nhưng mà t chưa viết frontend
   // Giờ viết nè!!
   @GetMapping("/product-detail/{productId}")
   public ResponseEntity<UserProductDetailResponse> getUserProductDetail(@PathVariable long productId) {
        return ResponseEntity.ok(productService.getUserProductDetail(productId));
    }

    //Thằng này dùng để kiếm product theo keyword, t cũng không biết nó chạy ổn không nữa ~~
    @GetMapping("/search/{keyword}")
    public ResponseEntity<ProductListResponse> searchProducts(@PathVariable String keyword) {
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }

    // SELLER AND ADMIN API ~~~~Noooo!!!

    // Lấy danh sách sản phẩm của SELLER. Cái này chỉ có Seller dùng được!!!
    @GetMapping("/product/my-products")
    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
    public ResponseEntity<ProductListWithTypeResponse> getMyProducts() {
        return ResponseEntity.ok(productService.getMyProducts());
    }

    @GetMapping("/product/my-product-detail/{productId}")
    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
    public ResponseEntity<SellerProductDetailResponse> getSellerProductDetail(@PathVariable long productId) {
        return ResponseEntity.ok(productService.getSellerProductDetail(productId));
    }

    //Theo t chỉ có SELLER mới có thể tạo Product thôi
    @PostMapping("/product/add-product")
    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
    public ResponseEntity<StatusResponse> addProduct(@RequestBody HandleProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    //Cập nhật product nè mấy ní!
    @PutMapping("/product/update-product/{productId}")
    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
    public ResponseEntity<StatusResponse> updateProduct(@PathVariable Long productId, @RequestBody HandleProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(productId, request));
    }

    //Delete nè
    @DeleteMapping("/product/delete-product/{productId}")
    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN')")
    public ResponseEntity<StatusResponse> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

}
