package com.example.backend.repository;

import com.example.backend.entity.Account;
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
    @Query("UPDATE Seller s SET s.fullname = :fullname WHERE s = :seller")
    void updateSellerFullNameBySeller(@Param("fullname") String fullname, @Param("seller") Seller seller);

    @Modifying
    @Transactional
    @Query("UPDATE Seller s SET s.phone = :phone WHERE s = :seller")
    void updateSellerPhoneBySeller(@Param("phone") String phone, @Param("seller") Seller seller);

    @Modifying
    @Transactional
    @Query("UPDATE Seller s SET s.address = :address WHERE s = :seller")
    void updateSellerAddressBySeller(@Param("address") String fullname, @Param("seller") Seller seller);


    @Query("SELECT s FROM Seller s WHERE s.account.username = :userName")
    Seller getSellerByUserName(@Param("userName") String userName);

}
