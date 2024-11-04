package com.example.backend.repository;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE ProductDetail p SET p.productPrice = :productDetailPrice WHERE p.product = :product")
    void updatePriceByProduct(@Param("productDetailPrice") long productDetailPrice, @Param("product") Product product);

    @Transactional
    @Modifying
    @Query("UPDATE ProductDetail p SET p.systemFee = :systemFee WHERE p.product = :product")
    void updateSystemFeeByProduct(@Param("systemFee") long systemFee, @Param("product") Product product);

    @Transactional
    @Modifying
    @Query("UPDATE ProductDetail p SET p.discount = :productDetailDiscount WHERE p.product = :product")
    void updateDiscountByProduct(@Param("productDetailDiscount") int productDetailDiscount, @Param("product") Product product);

    @Transactional
    @Modifying
    @Query("UPDATE ProductDetail p SET p.description = :productDetailDescription WHERE p.product = :product")
    void updateDescriptionByProduct(@Param("productDetailDescription") String productDetailDescription, @Param("product") Product product);

    @Transactional
    @Modifying
    @Query("UPDATE ProductDetail p SET p.imageUrl = :productDetailImage WHERE p.product = :product")
    void updateImageUrlByProduct(@Param("productDetailImage") String productDetailImage, @Param("product") Product product);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductDetail p WHERE p.id = :id")
    void deleteProductDetailById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE ProductDetail p SET p.sale = :sale WHERE p.product = :product")
    void updateSaleByProduct(@Param("sale") int sale, @Param("product") Product product);
}
