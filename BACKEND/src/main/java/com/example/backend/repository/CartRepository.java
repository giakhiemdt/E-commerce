package com.example.backend.repository;

import com.example.backend.entity.Cart;
import com.example.backend.entity.Status;
import com.example.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.users = :user AND c.status = :status")
    List<Cart> findCartByUserAndStatus(@Param("user") Users user, @Param("status") Status status);

}
