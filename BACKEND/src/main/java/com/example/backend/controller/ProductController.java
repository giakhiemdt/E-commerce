package com.example.backend.controller;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductType;
import com.example.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product-types")
    public ResponseEntity<List<ProductType>> getProductTypes() {
        return ResponseEntity.ok(productService.getAllProductType());
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping("/productid")
    public ResponseEntity<Optional<Product>> getProductById(@RequestParam long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/productname")
    public ResponseEntity<List<Product>> getProductByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }



}
