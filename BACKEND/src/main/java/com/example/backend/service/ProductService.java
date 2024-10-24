package com.example.backend.service;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductType;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    public List<ProductType> getAllProductType() {
        return productTypeRepository.findAll();
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findAllById(id);
    }

    public List<Product> getProductByName(String name) {
        return productRepository.findAllByName(name);
    }

    public List<Product> getProductByType(String typeName) {
        return productRepository.findProductByProductTypeName(typeName);
    }

    public List<Product> findProductBySellerId(long id) {
        return productRepository.findProductBySellerId(id);
    }
}
