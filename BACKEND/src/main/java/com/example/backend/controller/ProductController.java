package com.example.backend.controller;

import com.example.backend.model.request.frontend.seller.AddProductRequest;
import com.example.backend.model.request.frontend.seller.DeleteProductRequest;
import com.example.backend.model.request.frontend.seller.UpdateProductRequest;
import com.example.backend.model.response.ProductDetailResponse;
import com.example.backend.model.response.ProductTypesResponse;
import com.example.backend.model.response.ProductResponse;
import com.example.backend.model.response.admin.AdminProductResponse;
import com.example.backend.model.response.seller.SellerProductResponse;
import com.example.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    // SELLER API ~~~~Noooo!!!

    // Lấy danh sách sản phẩm của SELLER. Cái này chỉ có Seller dùng được!!!
    @GetMapping("/seller/my-products")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<Map<String, List<SellerProductResponse>>> getMyProducts() {
        return ResponseEntity.ok(productService.getMyProducts());
    }

    //Theo t chỉ có SELLER mới có thể tạo Product thôi
    @PostMapping("/seller/add-product")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<Boolean> addProduct(@RequestBody AddProductRequest addProductRequest) {
        productService.createProduct(addProductRequest);
        return ResponseEntity.ok().build();
    }

    //Cập nhật product nè mấy ní!
    @PostMapping("/seller/update-product")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<Boolean> updateProduct(@RequestBody UpdateProductRequest updateProductRequest) {
        productService.updateProduct(updateProductRequest);
        return ResponseEntity.ok().build();
    }

    //Delete nè
    @PostMapping("/seller/delete-product")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<Boolean> deleteProduct(@RequestBody DeleteProductRequest deleteProductRequest) {
        productService.deleteProduct(deleteProductRequest);
        return ResponseEntity.ok().build();
    }

    //ADMIN !!! AAAAA!!!!!

    //Lấy danh sách tất cả product chi tiết!
    @GetMapping("/admin/products")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, List<AdminProductResponse>>> getAllProductsWithDetail() {
        return ResponseEntity.ok(productService.getAllProductsWithDetail());
    }

}
