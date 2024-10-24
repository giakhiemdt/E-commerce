package com.example.backend.repository;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    Optional<Product> findAllById(Long id);

    List<Product> findAllByName(String name);

    @Query("SELECT p FROM Product p WHERE p.productType.name = :productTypeName")
    List<Product> findProductByProductTypeName(@Param("productTypeName") String productTypeName);

    @Query("SELECT p FROM Product p WHERE p.seller.id = :sellerId")
    List<Product> findProductBySellerId(@Param("sellerId") Long sellerId);
}

