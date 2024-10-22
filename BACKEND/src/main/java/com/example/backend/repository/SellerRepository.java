package com.example.backend.repository;

import com.example.backend.entity.Account;
import com.example.backend.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    public boolean existsByAccount(Account account);

}
