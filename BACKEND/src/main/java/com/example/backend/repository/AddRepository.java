package com.example.backend.repository;

import com.example.backend.entity.Add;
import com.example.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AddRepository extends JpaRepository<Add, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Add a SET a.quantity = :quantity WHERE a.cart = :cart AND a.product.id = :productId")
    void updateQuantityByCartAndProductId(@Param("quantity") int quantity, @Param("cart") Cart cart, @Param("productId") long productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Add a WHERE a.cart = :cart AND a.product.id = :productId")
    void deleteByCartAndProductId(@Param("cart") Cart cart, @Param("productId") long productId);
}
