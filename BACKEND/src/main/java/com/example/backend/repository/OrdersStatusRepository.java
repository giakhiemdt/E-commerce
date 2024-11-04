package com.example.backend.repository;

import com.example.backend.entity.OrdersStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrdersStatusRepository extends JpaRepository<OrdersStatus, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE OrdersStatus o SET o.needPaid = :needPaid WHERE o = :orderStatus")
    void updateTotalPriceByOrderStatus(@Param("needPaid") long needPaid, @Param("orderStatus") OrdersStatus orderStatus);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrdersStatus o WHERE o.orders.id = :orderId")
    void deleteOrderStatusByOrderId(@Param("orderId") long orderId);
}
