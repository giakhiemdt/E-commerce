package com.example.backend.repository;

import com.example.backend.entity.Account;
import com.example.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Users u SET u.fullname = :fullName WHERE u = :user")
    void updateUsersFullNameByUser(@Param("fullName") String fullName, @Param("user") Users users);

    @Transactional
    @Modifying
    @Query("UPDATE Users u SET u.phone = :phone WHERE u = :user")
    void updateUsersPhoneByUser(@Param("phone") String phone, @Param("user") Users users);

    @Transactional
    @Modifying
    @Query("UPDATE Users u SET u.phone = :address WHERE u = :user")
    void updateUsersAddressByUser(@Param("address") String address, @Param("user") Users users);

}
