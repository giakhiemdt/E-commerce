package com.example.backend.repository;

import com.example.backend.entity.Account;
import com.example.backend.entity.Product;
import com.example.backend.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Seller s WHERE s.account = :account")
    boolean existsByAccount(@Param("account") Account account);

    @Modifying
    @Transactional
    @Query("UPDATE Seller s SET s.fullname = :fullname WHERE s.account.id = :accountId")
    public void updateSellerByAccountIdAndFullname(@Param("fullname") String fullname, @Param("accountId") long accountId);

    @Modifying
    @Transactional
    @Query("UPDATE Seller s SET s.phone = :phone WHERE s.account.id = :accountId")
    public void updateSellerByAccountIdAndPhone(@Param("phone") String phone, @Param("accountId") long accountId);

    @Modifying
    @Transactional
    @Query("UPDATE Seller s SET s.address = :address WHERE s.account.id = :accountId")
    public void updateSellerByAccountIdAndAddress(@Param("address") String fullname, @Param("accountId") long accountId);

    @Query("SELECT s FROM Seller s WHERE s.account.id = : accountId")
    public Seller getSellerByAccountId(@Param("accountId") long accountId);

}
