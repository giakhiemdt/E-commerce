package com.example.backend.repository;

import com.example.backend.entity.Payment;
import com.example.backend.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT COUNT(p) FROM Payment p")
    Integer getNumberOfPayments();

    @Query("SELECT COUNT(p) FROM Payment p WHERE YEAR(p.createdDate) = :year")
    Integer countPaymentsByYear(@Param("year") int year);

    @Query("SELECT COUNT(p) FROM Payment p WHERE YEAR(p.createdDate) = :year AND MONTH(p.createdDate) = :month")
    Integer countPaymentsByYearAndMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT SUM(p.totalFee) FROM Payment p WHERE YEAR(p.createdDate) = :year AND MONTH(p.createdDate) = :month")
    Long sumPaymentsTotalFeeByYearAndMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT COUNT(p) FROM Payment p WHERE YEAR(p.createdDate) = :year AND p.seller = :seller")
    Integer countPaymentsByYearAndSeller(@Param("year") int year, @Param("seller") Seller seller);

    @Query("SELECT COUNT(p) FROM Payment p WHERE YEAR(p.createdDate) = :year AND MONTH(p.createdDate) = :month AND p.seller = :seller")
    Integer countPaymentsByYearAndMonthAndSeller(@Param("year") int year, @Param("month") int month, @Param("seller") Seller seller);

    @Query("SELECT SUM(p.totalFee) FROM Payment p WHERE YEAR(p.createdDate) = :year AND MONTH(p.createdDate) = :month AND p.seller = :seller")
    Long sumPaymentsTotalFeeByYearAndMonthAndSeller(@Param("year") int year, @Param("month") int month, @Param("seller") Seller seller);
}
