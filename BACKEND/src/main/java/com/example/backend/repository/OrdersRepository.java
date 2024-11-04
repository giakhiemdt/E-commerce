package com.example.backend.repository;

import com.example.backend.entity.Users;
import com.example.backend.entity.enums.OrderStatusEnum;
import com.example.backend.entity.Orders;
import com.example.backend.entity.enums.PaymentMethodEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.quantity = :quantity WHERE o.users = :users AND o.product.id = :productId")
    void updateQuantityByUserAndProductId(@Param("quantity") int quantity, @Param("users") Users users, @Param("productId") long productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Orders o WHERE o.users = :users AND o.product.id = :productId")
    void deleteByUserAndProductId(@Param("users") Users users, @Param("productId") long productId);

    @Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.status = :status WHERE o.id = :id")
    void updateStatusByOrderId(@Param("status") OrderStatusEnum status, @Param("id") long id);

    Optional<Orders> findById(long id);

    @Query("SELECT o FROM Orders o WHERE o.users = :users AND o.product.id = :productId")
    Optional<Orders> findByUserAndProductId(@Param("users") Users users, @Param("productId") long productId);

    @Transactional
    @Modifying
    @Query("UPDATE Orders o SET o.paymentMethod = :paymentMethod WHERE o.id = :id")
    void updatePaymentMethodById(@Param("paymentMethod") PaymentMethodEnum paymentMethod, @Param("id") long id);

}
