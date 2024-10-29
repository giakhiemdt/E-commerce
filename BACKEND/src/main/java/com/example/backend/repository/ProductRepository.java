package com.example.backend.repository;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductType;
import com.example.backend.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    Optional<Product> findAllById(Long id);

    List<Product> findAllByName(String name);

    List<Product> findByNameContaining(String keyword);

    @Query("SELECT p FROM Product p WHERE p.seller = :seller")
    List<Product> findAllBySeller(@Param("seller") Seller seller);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.productType = :productType WHERE p = :product")
    void updateProductByProductType(@Param("productType") ProductType productType, @Param("product") Product product);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.name = :name WHERE p = :product")
    void updateProductByName(@Param("name") String name, @Param("product") Product product);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.quantity = :quantity WHERE p = :product")
    void updateProductByQuantity(@Param("quantity") long quantity, @Param("product") Product product);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.isActive = :isActive WHERE p = :product")
    void updateProductByActive(@Param("isActive") Boolean isActive, @Param("product") Product product);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.productDetail.price = :productDetailPrice WHERE p = :product")
    void updateProductByProductDetailPrice(@Param("productDetailPrice") long productDetailPrice, @Param("product") Product product);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.productDetail.discount = :productDetailDiscount WHERE p = :product")
    void updateProductByProductDetailDiscount(@Param("productDetailDiscount") String productDetailDiscount, @Param("product") Product product);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.productDetail.description = :productDetailDescription WHERE p = :product")
    void updateProductByProductDetailDescription(@Param("productDetailDescription") String productDetailDescription, @Param("product") Product product);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.productDetail.imageUrl = :productDetailImage WHERE p = :product")
    void updateProductByProductDetailImageUrl(@Param("productDetailImage") String productDetailImage, @Param("product") Product product);

    @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = :id")
    void deleteProductById(@Param("id") Long id);
}

